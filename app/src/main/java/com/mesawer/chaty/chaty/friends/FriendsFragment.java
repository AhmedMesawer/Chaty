package com.mesawer.chaty.chaty.friends;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.add_friend.AddFriendActivity;
import com.mesawer.chaty.chaty.base.BaseFragment;
import com.mesawer.chaty.chaty.chatting.ChattingActivity;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static android.app.Activity.RESULT_OK;
import static com.mesawer.chaty.chaty.add_friend.AddFriendActivity.ADD_FRIEND_REQUEST_CODE;
import static com.mesawer.chaty.chaty.chatting.ChattingActivity.FRIEND_INTENT_KEY;
import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;
import static com.mesawer.chaty.chaty.main.MainActivity.SHARED_PREFERENCES_FILE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends BaseFragment implements FriendsContract.View {

    @BindView(R.id.friends_rv)
    RecyclerView friendsRv;
    LinearLayoutManager llm;
    FriendsAdapter friendsAdapter;
    List<User> friends;
    Unbinder unbinder;
    User user;
    @BindView(R.id.friends_fragment_layout)
    FrameLayout friendsFragmentLayout;
    FriendsContract.Presenter friendPresenter;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    private SharedPreferences preferences;
    private PublishSubject<User> friendClickedObserver = PublishSubject.create();
    private Disposable disposable;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        unbinder = ButterKnife.bind(this, view);
        super.view = friendsFragmentLayout;
        friendPresenter = new FriendsPresenter(this,
                Injection.provideFirebaseFriendsRepository());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            user = intent.getParcelableExtra(CURRENT_USER_INTENT_KEY);

        }
        setupFriendsRecyclerView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FRIEND_REQUEST_CODE && resultCode == RESULT_OK) {
            user = data.getParcelableExtra(CURRENT_USER_INTENT_KEY);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user != null) {
            friendPresenter.getFriends(user);
        }
        disposable = friendClickedObserver.subscribe(this::navigateToChattingActivity);
    }

    @Override
    public void onStop() {
        super.onStop();

        disposable.dispose();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void showFriends(List<User> friends) {
        friendsAdapter.setFriends(friends);
    }

    @Override
    public void navigateToChattingActivity(User friend) {
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
        intent.putExtra(CURRENT_USER_INTENT_KEY, user);
        intent.putExtra(FRIEND_INTENT_KEY, friend);
        startActivity(intent);
    }

    @Override
    public void navigateToAddFriendActivity() {
        Intent intent = new Intent(getActivity(), AddFriendActivity.class);
        intent.putExtra(CURRENT_USER_INTENT_KEY, user);
        getActivity().startActivityForResult(intent, ADD_FRIEND_REQUEST_CODE);
    }

    private void setupFriendsRecyclerView() {
        llm = new LinearLayoutManager(getContext());
        friendsAdapter = new FriendsAdapter(new ArrayList<>(), friendClickedObserver);
        friendsRv.setLayoutManager(llm);
        friendsRv.setAdapter(friendsAdapter);
    }

    private void saveStateInPreferences(String key, Object value) {
        preferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_FILE_KEY,
                Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            }
            editor.apply();
        }
    }

    @OnClick(R.id.floatingActionButton)
    public void onViewClicked() {
        navigateToAddFriendActivity();
    }
}
