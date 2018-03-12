package com.mesawer.chaty.chaty.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilias on 05/03/2018.
 */

public class User implements Parcelable {

    private String userId;
    private String userName;
    private String email;
    private String password;
    private List<String> friends;
    private List<String> chats;
    private List<String> outgoingRequests;
    private List<String> incomingRequests;

    public User() {
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        friends = new ArrayList<>();
        chats = new ArrayList<>();
        outgoingRequests = new ArrayList<>();
        incomingRequests = new ArrayList<>();
    }

    //region Setters and Getters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public void addFriendRequest(String friendRequest) {

        if (outgoingRequests != null) outgoingRequests.add(friendRequest);
        else {
            outgoingRequests = new ArrayList<>();
            outgoingRequests.add(friendRequest);
        }
    }

    public List<String> getIncomingRequests() {
        return incomingRequests;
    }

    public void removeFriendRequest(String friendRequest) {

        if (outgoingRequests != null && outgoingRequests.contains(friendRequest))
            outgoingRequests.remove(friendRequest);
    }

    public List<String> getOutgoingRequests() {
        return outgoingRequests;
    }

    public void setChats(List<String> chats) {
        this.chats = chats;
    }

    public List<String> getChats() {
        return chats;
    }
    //endregion

    //region Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeStringList(this.friends);
        dest.writeStringList(this.chats);
        dest.writeStringList(this.outgoingRequests);
        dest.writeStringList(this.incomingRequests);
    }

    protected User(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.friends = in.createStringArrayList();
        this.chats = in.createStringArrayList();
        this.outgoingRequests = in.createStringArrayList();
        this.incomingRequests = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
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
