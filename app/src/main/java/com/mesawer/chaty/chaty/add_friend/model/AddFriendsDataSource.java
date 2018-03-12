package com.mesawer.chaty.chaty.add_friend.model;

import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 08/03/2018.
 */

public interface AddFriendsDataSource {

    void getUsers(User user,
                  SuccessfulResponseWithResultCallback<User> resultCallback,
                  FailedResponseCallback failedCallback);

    void sendFriendRequest(User current, User userToSend,
                  SuccessfulResponseWithResultCallback<User> resultCallback,
                  FailedResponseCallback failedCallback);

    void cancelFriendRequest(User current, User userToSend,
                  SuccessfulResponseWithResultCallback<User> resultCallback,
                  FailedResponseCallback failedCallback);
}
