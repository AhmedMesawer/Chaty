package com.mesawer.chaty.chaty.friends;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendsContract {

    interface View extends IView{
        void showFriends(List<User> friend);

        void navigateToChatActivity(User friend);

        void navigateToAddFriendActivity();

    }

    interface Presenter{
        void getFriends(User user);
    }
}
