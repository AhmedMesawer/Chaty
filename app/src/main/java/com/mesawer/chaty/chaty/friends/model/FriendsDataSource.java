package com.mesawer.chaty.chaty.friends.model;

import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendsDataSource {

    Maybe<List<User>> getFriends(User user);
}
