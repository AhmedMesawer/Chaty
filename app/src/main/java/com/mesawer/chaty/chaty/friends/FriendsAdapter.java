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

//    public FriendsAdapter(List<User> friends, PublishSubject<User> friendClickedObservable) {
//        this.friends = friends;
//        this.friendClickedObservable = friendClickedObservable;
//    }

    public FriendsAdapter(List<User> friends) {
        this.friends = friends;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_item, parent, false);
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        User user = friends.get(position);
        holder.friendNameTextView.setText(user.getUserName());
//        holder.itemView.setOnClickListener(view -> friendClickedObservable.onNext(user));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public void setFriends(List<User> friends){
        this.friends = friends;
        notifyDataSetChanged();
    }

    public void add(User friend){
        this.friends.add(friend);
        notifyDataSetChanged();
    }

    class FriendsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.friend_name_text_view)
        TextView friendNameTextView;
        @BindView(R.id.friend_profile_image_view)
        ImageView friendProfileImageView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //TODO convert item click listener for each recycler view to rx
}
