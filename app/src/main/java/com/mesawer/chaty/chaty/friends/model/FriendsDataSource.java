package com.mesawer.chaty.chaty.friends.model;

import android.icu.lang.UScript;

import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;

import java.util.List;

/**
 * Created by ilias on 08/03/2018.
 */

public interface FriendsDataSource {

    void getFriends(List<String> uid,
                    SuccessfulResponseWithResultCallback<List<User>> resultCallback,
                    FailedResponseCallback failedCallback);
}
