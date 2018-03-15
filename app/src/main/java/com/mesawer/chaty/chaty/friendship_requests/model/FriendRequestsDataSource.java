package com.mesawer.chaty.chaty.friendship_requests.model;

import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendRequestsDataSource {

    Maybe<List<User>> getFriendRequests(User user);

    Maybe<User> acceptFriendRequest(User current, User userToAccept);

    Maybe<User> ignoreFriendRequest(User current, User userToIgnore);
}
