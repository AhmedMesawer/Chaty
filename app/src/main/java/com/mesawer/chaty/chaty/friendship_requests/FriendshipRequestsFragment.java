package com.mesawer.chaty.chaty.friendship_requests;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendshipRequestsFragment extends BaseFragment {


    @BindView(R.id.friend_requests_rv)
    RecyclerView friendRequestsRv;
    @BindView(R.id.friend_requests_layout)
    FrameLayout friendRequestsLayout;
    Unbinder unbinder;

    public FriendshipRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friendship_requests, container, false);
        unbinder = ButterKnife.bind(this, view);
        super.view = friendRequestsLayout;
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
