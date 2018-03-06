package com.mesawer.chaty.chaty.registration.model;

import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 05/03/2018.
 */

public interface RegistrationDataSource {
    void registerNewUser(User user,
                         SuccessfulResponseWithResultCallback<User> resultCallback,
                         FailedResponseCallback failedCallback);
}
