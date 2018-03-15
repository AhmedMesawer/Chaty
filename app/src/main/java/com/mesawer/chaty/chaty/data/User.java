package com.mesawer.chaty.chaty.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ilias on 05/03/2018.
 */

public class User implements Parcelable {

    private String userId;
    private String userName;
    private String email;
    private String password;
    private Map<String, String> friends = new HashMap<>();
    private Map<String, String> chats = new HashMap<>();
    private Map<String, String> outgoingRequests = new HashMap<>();
    private Map<String, String> incomingRequests = new HashMap<>();

    public User() {
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
//        friends = new HashMap<>();
//        chats = new HashMap<>();
//        outgoingRequests = new HashMap<>();
//        incomingRequests = new HashMap<>();
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

    public Map<String, String> getFriends() {
        return friends;
    }

    public Map<String, String> getChats() {
        return chats;
    }

    public Map<String, String> getOutgoingRequests() {
        return outgoingRequests;
    }

    public Map<String, String> getIncomingRequests() {
        return incomingRequests;
    }

    public void addOutgoingRequest(String friendRequest) {

        if (outgoingRequests != null) outgoingRequests.put(friendRequest, new Date().toString());
        else {
            outgoingRequests = new HashMap<>();
            outgoingRequests.put(friendRequest, new Date().toString());
        }
    }

    public void addIncomingRequest(String friendRequest) {

        if (incomingRequests != null) incomingRequests.put(friendRequest, new Date().toString());
        else {
            incomingRequests = new HashMap<>();
            incomingRequests.put(friendRequest, new Date().toString());
        }
    }

    public void removeOutgoingRequest(String friendRequest) {

        if (outgoingRequests != null && outgoingRequests.containsKey(friendRequest))
            outgoingRequests.remove(friendRequest);
    }

    public void removeIncomingRequest(String friendRequest) {

        if (incomingRequests != null && incomingRequests.containsKey(friendRequest))
            incomingRequests.remove(friendRequest);
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
        dest.writeInt(this.friends.size());
        for (Map.Entry<String, String> entry : this.friends.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.chats.size());
        for (Map.Entry<String, String> entry : this.chats.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.outgoingRequests.size());
        for (Map.Entry<String, String> entry : this.outgoingRequests.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeInt(this.incomingRequests.size());
        for (Map.Entry<String, String> entry : this.incomingRequests.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    protected User(Parcel in) {
        this.userId = in.readString();
        this.userName = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        int friendsSize = in.readInt();
        this.friends = new HashMap<String, String>(friendsSize);
        for (int i = 0; i < friendsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.friends.put(key, value);
        }
        int chatsSize = in.readInt();
        this.chats = new HashMap<String, String>(chatsSize);
        for (int i = 0; i < chatsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.chats.put(key, value);
        }
        int outgoingRequestsSize = in.readInt();
        this.outgoingRequests = new HashMap<String, String>(outgoingRequestsSize);
        for (int i = 0; i < outgoingRequestsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.outgoingRequests.put(key, value);
        }
        int incomingRequestsSize = in.readInt();
        this.incomingRequests = new HashMap<String, String>(incomingRequestsSize);
        for (int i = 0; i < incomingRequestsSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.incomingRequests.put(key, value);
        }
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
