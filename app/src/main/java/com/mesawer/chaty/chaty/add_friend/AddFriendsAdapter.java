package com.mesawer.chaty.chaty.add_friend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.data.Action;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ilias on 08/03/2018.
 */

public class AddFriendsAdapter extends RecyclerView.Adapter<AddFriendsAdapter.AddFriendsViewHolder> {

    private List<User> friends;
    private User current;
    private PublishSubject<Action> addFriendClickedObservable;
    private PublishSubject<User> friendRequestObserver;

    public AddFriendsAdapter(List<User> friends, User current,
                             PublishSubject<Action> addFriendClickedObservable,
                             PublishSubject<User> friendRequestObserver) {
        this.friends = friends;
        this.current = current;
        this.addFriendClickedObservable = addFriendClickedObservable;
        this.friendRequestObserver = friendRequestObserver;
    }

    @Override
    public AddFriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_friend_item, parent, false);
        return new AddFriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddFriendsViewHolder holder, int position) {
        User user = friends.get(position);
//        holder.addFriendProfileImageView.setImageResource();
        holder.addFriendTextView.setText(user.getUserName());
        if (current.getOutgoingRequests() != null) {
            if (current.getOutgoingRequests().containsKey(user.getUserId())) {
                holder.addFriendButton.setText(R.string.cancel_request);
            }
        }
        friendRequestObserver.subscribe(user1 -> {
            if (user1.getUserId().equals(user.getUserId())) {
                if (holder.addFriendButton.getText().equals("Cancel Request")) {
                    holder.addFriendButton.setText(R.string.add_friend);
                } else {
                    holder.addFriendButton.setText(R.string.cancel_request);
                }
            }
        });
        holder.addFriendButton.setOnClickListener(
                view -> {
                    Action action = new Action(user);
                    if (holder.addFriendButton.getText().equals("Add Friend")) {
                        action.setAction("add");
                    } else if (holder.addFriendButton.getText().equals("Cancel Request")) {
                        action.setAction("cancel");
                    }
                    addFriendClickedObservable.onNext(action);
                });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
        notifyDataSetChanged();
    }

    public void add(User friend) {
        this.friends.add(friend);
        notifyDataSetChanged();
    }

    class AddFriendsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.add_friend_profile_image_view)
        ImageView addFriendProfileImageView;
        @BindView(R.id.add_friend_name_text_view)
        TextView addFriendTextView;
        @BindView(R.id.add_friend_button)
        Button addFriendButton;

        AddFriendsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
