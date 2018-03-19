package com.mesawer.chaty.chaty.chats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseFragment;
import com.mesawer.chaty.chaty.chatting.ChattingActivity;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

import static com.mesawer.chaty.chaty.chatting.ChattingActivity.FRIEND_INTENT_KEY;
import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends BaseFragment implements ChatsContract.View {


    @BindView(R.id.chats_rv)
    RecyclerView chatsRv;
    Unbinder unbinder;
    @BindView(R.id.chats_layout)
    FrameLayout chatsLayout;
    ChatsAdapter chatsAdapter;
    LinearLayoutManager llm;
    private PublishSubject<User> chatClickedObserver = PublishSubject.create();
    private User current;
    private Disposable disposable;
    private ChatsContract.Presenter chatsPresenter;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        unbinder = ButterKnife.bind(this, view);
        super.view = chatsLayout;
        chatsPresenter = new ChatsPresenter(this,
                Injection.provideFirebaseChatsRepository());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        if (intent != null)
            current = intent.getParcelableExtra(CURRENT_USER_INTENT_KEY);
        setupFriendsRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (current != null)
            chatsPresenter.getChats(current);
        disposable = chatClickedObserver.subscribe(this::navigateToChattingActivity);
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
    public void showChats(List<User> chats) {
        chatsAdapter.setChats(chats);
    }

    @Override
    public void navigateToChattingActivity(User friend) {
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
        intent.putExtra(CURRENT_USER_INTENT_KEY, current);
        intent.putExtra(FRIEND_INTENT_KEY, friend);
        startActivity(intent);
    }

    private void setupFriendsRecyclerView() {
        llm = new LinearLayoutManager(getActivity());
        chatsAdapter = new ChatsAdapter(new ArrayList<>(), chatClickedObserver);
        chatsRv.setLayoutManager(llm);
        chatsRv.setAdapter(chatsAdapter);
    }
}
