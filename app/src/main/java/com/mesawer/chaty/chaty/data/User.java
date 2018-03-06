package com.mesawer.chaty.chaty.data;

import java.util.List;
import java.util.UUID;

/**
 * Created by ilias on 05/03/2018.
 */

public class User {

    private String userId;
    private String userName;
    private String email;
    private String password;
    private List<User> friends;
    private List<Chat> chats;

    public User(String userName, String email, String password) {
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
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

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
