package com.mesawer.chaty.chaty.add_friend.model;

import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by ilias on 08/03/2018.
 */

public interface AddFriendsDataSource {

    Maybe<List<User>> getUsers(User user);

    Maybe<User> sendFriendRequest(User current, User userToSend);

    Maybe<User> cancelFriendRequest(User current, User userToSend);
}
