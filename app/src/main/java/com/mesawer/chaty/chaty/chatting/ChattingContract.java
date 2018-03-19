package com.mesawer.chaty.chaty.chatting;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.Message;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 08/03/2018.
 */

public interface ChattingContract {

    interface View extends IView {
        void showMessages(Message message);

        void sendMessage();

    }

    interface Presenter {
        void getChat(User current, User friend);

        void newMessage(User current, User friend, Message message);
    }
}
