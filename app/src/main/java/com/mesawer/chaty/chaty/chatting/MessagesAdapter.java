package com.mesawer.chaty.chaty.chatting;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.data.Message;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ilias on 08/03/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages;
    private User current;
    private User friend;

    public MessagesAdapter(List<Message> messages, User current, User friend) {
        this.messages = messages;
        this.current = current;
        this.friend = friend;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == R.layout.current_user_message_item) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.current_user_message_item, parent, false);
            return new CurrentUserMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.friend_user_message_item, parent, false);
            return new FriendMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder instanceof CurrentUserMessageViewHolder) {
            ((CurrentUserMessageViewHolder) holder).
                    currentMessageTextView.setText(message.getMessage());
        } else {
            ((FriendMessageViewHolder) holder).
                    friendMessageTextView.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getFrom().equals(current.getUserId()))
            return R.layout.current_user_message_item;
        return R.layout.friend_user_message_item;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    class CurrentUserMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.current_message_text_view)
        TextView currentMessageTextView;

        CurrentUserMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FriendMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.friend_message_text_view)
        TextView friendMessageTextView;

        FriendMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
