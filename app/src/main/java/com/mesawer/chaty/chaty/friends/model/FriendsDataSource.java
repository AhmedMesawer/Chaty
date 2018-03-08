package com.mesawer.chaty.chaty.friends.model;

import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendsDataSource {

    void getFriends(User user,
                    SuccessfulResponseWithResultCallback<User> resultCallback,
                    FailedResponseCallback failedCallback);
}
