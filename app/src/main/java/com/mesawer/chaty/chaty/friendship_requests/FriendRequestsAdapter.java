package com.mesawer.chaty.chaty.friendship_requests;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

public class FriendRequestsAdapter extends RecyclerView.Adapter<FriendRequestsAdapter.FriendRequestsViewHolder> {

    private List<User> friendRequests;
    private User current;
    private PublishSubject<Action> addFriendClickedObservable;

    public FriendRequestsAdapter(List<User> friendRequests, User current,
                                 PublishSubject<Action> addFriendClickedObservable) {
        this.friendRequests = friendRequests;
        this.current = current;
        this.addFriendClickedObservable = addFriendClickedObservable;
    }

    @Override
    public FriendRequestsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_request_item, parent, false);
        return new FriendRequestsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendRequestsViewHolder holder, int position) {
        User friendRequest = friendRequests.get(position);
        if (friendRequest.getPhotoUrl() != null) {
            Glide.with(holder.friendRequestProfileImageView.getContext())
                    .load(friendRequest.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.friendRequestProfileImageView);
        }
        holder.friendRequestTextView.setText(friendRequest.getUserName());
        Action action = new Action(friendRequest);
        holder.acceptFriendRequestButton.setOnClickListener(
                view -> {
                    action.setAction("accept");
                    addFriendClickedObservable.onNext(action);
                });
        holder.ignoreFriendRequestButton.setOnClickListener(
                view -> {
                    action.setAction("ignore");
                    addFriendClickedObservable.onNext(action);
                });
    }

    @Override
    public int getItemCount() {
        return friendRequests.size();
    }

    public void setFriendRequests(List<User> friendRequests) {
        this.friendRequests = friendRequests;
        notifyDataSetChanged();
    }

    public void add(User friendRequest) {
        if (friendRequests.contains(friendRequest)) {
            friendRequests.remove(friendRequest);
            friendRequests.add(friendRequest);
        } else this.friendRequests.add(friendRequest);
        notifyDataSetChanged();
    }

    public void remove(User friendRequest) {
        this.friendRequests.remove(friendRequest);
        notifyDataSetChanged();
    }

    class FriendRequestsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.friend_request_profile_image_view)
        ImageView friendRequestProfileImageView;
        @BindView(R.id.friend_request_text_view)
        TextView friendRequestTextView;
        @BindView(R.id.accept_friend_request_button)
        Button acceptFriendRequestButton;
        @BindView(R.id.ignore_friend_request_button)
        Button ignoreFriendRequestButton;

        FriendRequestsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
