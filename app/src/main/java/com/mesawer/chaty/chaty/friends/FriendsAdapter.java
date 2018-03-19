package com.mesawer.chaty.chaty.friends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ilias on 08/03/2018.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private List<User> friends;
    private PublishSubject<User> friendClickedObservable;

    public FriendsAdapter(List<User> friends, PublishSubject<User> friendClickedObservable) {
        this.friends = friends;
        this.friendClickedObservable = friendClickedObservable;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_item, parent, false);
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        User friend = friends.get(position);
        holder.friendNameTextView.setText(friend.getUserName());
        holder.itemView.setOnClickListener(view -> friendClickedObservable.onNext(friend));
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
        if (friends.isEmpty())
            friends.add(friend);
        else {
            for (User f : friends) {
                if (f.getUserId().equals(friend.getUserId())) {
                    friends.remove(friend);
                    friends.add(friend);
                    notifyDataSetChanged();
                } else friends.add(friend);
            }
        }
        notifyDataSetChanged();
    }

    class FriendsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.friend_name_text_view)
        TextView friendNameTextView;
        @BindView(R.id.friend_profile_image_view)
        ImageView friendProfileImageView;

        FriendsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
