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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.PublishSubject;

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
    private PublishSubject<User> chatClickedObserver;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        unbinder = ButterKnife.bind(this, view);
        super.view = chatsLayout;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupFriendsRecyclerView();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showChats(List<User> friend) {

    }

    @Override
    public void navigateToChattingActivity() {
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
//        intent.putExtra();
        startActivity(intent);
    }

    private void setupFriendsRecyclerView() {
        llm = new LinearLayoutManager(getActivity());
        chatsAdapter = new ChatsAdapter(new ArrayList<>(), chatClickedObserver);
        chatsRv.setLayoutManager(llm);
        chatsRv.setAdapter(chatsAdapter);
    }
}
