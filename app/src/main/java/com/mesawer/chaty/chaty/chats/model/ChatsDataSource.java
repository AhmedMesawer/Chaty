package com.mesawer.chaty.chaty.chats.model;

import com.mesawer.chaty.chaty.data.User;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Created by ilias on 08/03/2018.
 */

public interface ChatsDataSource {

    Maybe<List<User>> getChats(User user);
}
