package com.mesawer.chaty.chaty.login.model;

import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 05/03/2018.
 */

public interface LoginDataSource {
    void login(String email, String password,
               SuccessfulResponseWithResultCallback<User> resultCallback,
               FailedResponseCallback failedCallback);
}
