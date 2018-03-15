package com.mesawer.chaty.chaty.friendship_requests;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendRequestsContract {

    interface View extends IView{
        void showFriendRequests(List<User> friendRequests);

        void navigateToProfileActivity(User friend);

        void removeAcceptedRequestFromList(User acceptedUser);

        void removeIgnoredRequestFromList(User ignoredUser);

    }

    interface Presenter{
        void getFriendRequests(User current);

        void acceptFriendRequest(User current, User userToAccept);

        void ignoreFriendRequest(User current, User userToIgnore);
    }
}
