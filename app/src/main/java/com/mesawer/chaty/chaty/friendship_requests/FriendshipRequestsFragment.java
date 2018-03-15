package com.mesawer.chaty.chaty.friendship_requests;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseFragment;
import com.mesawer.chaty.chaty.data.Action;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendshipRequestsFragment extends BaseFragment implements FriendRequestsContract.View {


    @BindView(R.id.friend_requests_rv)
    RecyclerView friendRequestsRv;
    @BindView(R.id.friend_requests_layout)
    FrameLayout friendRequestsLayout;
    Unbinder unbinder;
    LinearLayoutManager llm;
    FriendRequestsAdapter friendRequestsAdapter;
    private FriendRequestsContract.Presenter friendRequestsPresenter;
    private User currentUser;
    private PublishSubject<Action> friendRequestActionObserver = PublishSubject.create();
    private Disposable disposable;

    public FriendshipRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friendship_requests, container, false);
        unbinder = ButterKnife.bind(this, view);
        super.view = friendRequestsLayout;
        friendRequestsPresenter = new FriendRequestsPresenter(this,
                Injection.provideFirebaseFriendRequestsRepository());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            currentUser = intent.getParcelableExtra(CURRENT_USER_INTENT_KEY);
        }
        setupFriendRequestsRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentUser != null)
            friendRequestsPresenter.getFriendRequests(currentUser);
        disposable = friendRequestActionObserver.subscribe(
                action -> {
                    if (action.getAction().equals("accept"))
                        friendRequestsPresenter.acceptFriendRequest(currentUser, action.getUser());
                    else
                        friendRequestsPresenter.ignoreFriendRequest(currentUser, action.getUser());

                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        disposable.dispose();
        unbinder.unbind();
    }

    @Override
    public void showFriendRequests(List<User> friendRequests) {
        friendRequestsAdapter.setFriendRequests(friendRequests);
    }

    @Override
    public void navigateToProfileActivity(User friend) {

    }

    @Override
    public void removeAcceptedRequestFromList(User acceptedUser) {
        friendRequestsAdapter.remove(acceptedUser);
        Snackbar.make(friendRequestsLayout,
                "you and " + acceptedUser.getUserName() + " are friend now",
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void removeIgnoredRequestFromList(User ignoredUser) {
        friendRequestsAdapter.remove(ignoredUser);
    }

    private void setupFriendRequestsRecyclerView() {
        llm = new LinearLayoutManager(getActivity());
        friendRequestsAdapter = new FriendRequestsAdapter(new ArrayList<>(),
                currentUser, friendRequestActionObserver);
        friendRequestsRv.setLayoutManager(llm);
        friendRequestsRv.setAdapter(friendRequestsAdapter);
    }
}
