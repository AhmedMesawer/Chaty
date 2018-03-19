package com.mesawer.chaty.chaty.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ilias on 05/03/2018.
 */

public class Message implements Parcelable {

    private String from;
    private String message;

    public Message() {}

    public Message(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.from);
        dest.writeString(this.message);
    }

    protected Message(Parcel in) {
        this.from = in.readString();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
