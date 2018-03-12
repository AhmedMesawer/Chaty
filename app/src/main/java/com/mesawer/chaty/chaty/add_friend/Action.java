package com.mesawer.chaty.chaty.add_friend;

import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 12/03/2018.
 */

public class Action {

    private User user;
    private String action;

    public Action(User user) {
        this.user = user;
    }

    public Action(User user, String action) {
        this.user = user;
        this.action = action;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
