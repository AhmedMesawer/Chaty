package com.mesawer.chaty.chaty.friends;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendsContract {

    interface View extends IView{
        void showFriends(User friend);

        void navigateToChatActivity(User friend);
    }

    interface Presenter{
        void getFriends(User user);
    }
}
