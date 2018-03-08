package com.mesawer.chaty.chaty.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ilias on 05/03/2018.
 */

public class User implements Parcelable {

//    private String userId;
    private String userName;
    private String email;
    private String password;
    private List<String> friends;
    private List<String> chats;

    public User() {}

    public User(String userName, String email, String password) {
//        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
        this.email = email;
        this.password = password;
        friends = new ArrayList<>();
//        friends.add("");
        chats = new ArrayList<>();
//        chats.add("");
    }

    //region Setters and Getters
//    public String getUserId() {
//        return userId;
//    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<String> getChats() {
        return chats;
    }

    public void setChats(List<String> chats) {
        this.chats = chats;
    }
    //endregion

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeStringList(this.friends);
        dest.writeStringList(this.chats);
    }

    protected User(Parcel in) {
//        this.userId = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.friends = in.createStringArrayList();
        this.chats = in.createStringArrayList();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    //endregion
}
