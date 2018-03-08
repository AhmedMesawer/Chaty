package com.mesawer.chaty.chaty.friendship_requests;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesawer.chaty.chaty.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendshipRequestsFragment extends Fragment {


    public FriendshipRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friendship_requests, container, false);
    }

}
