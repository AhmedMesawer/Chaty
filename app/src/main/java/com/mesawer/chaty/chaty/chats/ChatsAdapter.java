package com.mesawer.chaty.chaty.chats;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by ilias on 08/03/2018.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {

    private List<User> chats;
    private PublishSubject<User> chatClickedObservable;

    public ChatsAdapter(List<User> chats, PublishSubject<User> chatClickedObservable) {
        this.chats = chats;
        this.chatClickedObservable = chatClickedObservable;
    }

    @Override
    public ChatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_chat_item, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatsViewHolder holder, int position) {
        User friend = chats.get(position);
        if (friend.getPhotoUrl() != null) {
            Glide.with(holder.friendProfileImageView.getContext())
                    .load(friend.getPhotoUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.friendProfileImageView);
        }
        holder.friendNameTextView.setText(friend.getUserName());
        holder.itemView.setOnClickListener(view -> chatClickedObservable.onNext(friend));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void setChats(List<User> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    public void add(User friend) {
        if (chats.isEmpty())
            chats.add(friend);
        else {
            for (User f : chats) {
                if (f.getUserId().equals(friend.getUserId())) {
                    chats.remove(friend);
                    chats.add(friend);
                    notifyDataSetChanged();
                } else chats.add(friend);
            }
        }
        notifyDataSetChanged();
    }

    class ChatsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.friend_profile_image_view)
        ImageView friendProfileImageView;
        @BindView(R.id.friend_name_text_view)
        TextView friendNameTextView;
        @BindView(R.id.last_message_text_view)
        TextView lastMessageTextView;

        ChatsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
