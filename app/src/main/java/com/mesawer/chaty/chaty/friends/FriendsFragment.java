package com.mesawer.chaty.chaty.friends;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.data.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mesawer.chaty.chaty.main.MainActivity.USER_INTENT_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {


    @BindView(R.id.friends_rv)
    RecyclerView friendsRv;
    LinearLayoutManager llm;
    FriendsAdapter friendsAdapter;
    List<User> friends;
    Unbinder unbinder;
    User user;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupFriendsRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        if (intent != null){
            user = intent.getParcelableExtra(USER_INTENT_KEY);
        }
    }

    private void setupFriendsRecyclerView() {
        if (user != null)
        user.getFriends();
        llm = new LinearLayoutManager(getContext());
        friendsAdapter = new FriendsAdapter(new ArrayList<>());
        friendsRv.setLayoutManager(llm);
        friendsRv.setAdapter(friendsAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
