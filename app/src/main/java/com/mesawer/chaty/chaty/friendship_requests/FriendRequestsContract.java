package com.mesawer.chaty.chaty.friendship_requests;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendRequestsContract {

    interface View extends IView{
        void showFriendRequests(User friendRequest);

        void navigateToProfileActivity(User friend);

    }

    interface Presenter{
        void getFriendRequests(User friendRequest);

        void acceptFriendRequest(User current, User userToAccept);

        void ignoreFriendRequest(User current, User userToIgnore);
    }
}
