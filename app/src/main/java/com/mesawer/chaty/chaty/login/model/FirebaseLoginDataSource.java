package com.mesawer.chaty.chaty.login.model;

import com.google.firebase.auth.FirebaseAuth;
import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 05/03/2018.
 */

public class FirebaseLoginDataSource implements LoginDataSource {

    private static FirebaseLoginDataSource INSTANCE;
    private FirebaseAuth auth;

    private FirebaseLoginDataSource() {
        auth = FirebaseAuth.getInstance();
    }

    public static FirebaseLoginDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseLoginDataSource();

        return INSTANCE;
    }

    @Override
    public void login(String email, String password, SuccessfulResponseWithResultCallback<User> resultCallback, FailedResponseCallback failedCallback) {

    }
}
