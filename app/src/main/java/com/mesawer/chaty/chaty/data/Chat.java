package com.mesawer.chaty.chaty.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilias on 05/03/2018.
 */

public class Chat implements Parcelable {

    private String first_user_id;
    private String second_user_id;
    private List<Message> messages;

    public Chat() {}

    public Chat(String first_user_id, String second_user_id) {
        this.first_user_id = first_user_id;
        this.second_user_id = second_user_id;
        messages = new ArrayList<>();
    }

    public String getFirst_user_id() {
        return first_user_id;
    }

    public String getSecond_user_id() {
        return second_user_id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.first_user_id);
        dest.writeString(this.second_user_id);
        dest.writeList(this.messages);
    }

    protected Chat(Parcel in) {
        this.first_user_id = in.readString();
        this.second_user_id = in.readString();
        this.messages = new ArrayList<Message>();
        in.readList(this.messages, Message.class.getClassLoader());
    }

    public static final Parcelable.Creator<Chat> CREATOR = new Parcelable.Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel source) {
            return new Chat(source);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };
}
