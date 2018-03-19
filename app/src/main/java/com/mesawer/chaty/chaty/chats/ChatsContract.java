package com.mesawer.chaty.chaty.chats;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

/**
 * Created by ilias on 08/03/2018.
 */

public interface ChatsContract {

    interface View extends IView{
        void showChats(List<User> chats);

        void navigateToChattingActivity(User friend);

    }

    interface Presenter{
        void getChats(User user);
    }
}
