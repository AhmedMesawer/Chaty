package com.mesawer.chaty.chaty.chatting.model;

import com.mesawer.chaty.chaty.data.Message;
import com.mesawer.chaty.chaty.data.User;

import io.reactivex.Observable;

/**
 * Created by ilias on 08/03/2018.
 */

public interface ChattingDataSource {

    Observable<Message> getMessages(User current, User friend);

    void createChat(User current, User friend);

    void newMessage(User current, User friend, Message message);
}
