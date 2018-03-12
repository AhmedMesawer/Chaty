package com.mesawer.chaty.chaty.registration.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.UUID;

/**
 * Created by ilias on 05/03/2018.
 */

public class FirebaseRegistrationDataSource implements RegistrationDataSource {

    private static FirebaseRegistrationDataSource INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private FirebaseRegistrationDataSource() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference().child("users");
    }

    public static FirebaseRegistrationDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseRegistrationDataSource();

        return INSTANCE;
    }

    @Override
    public void registerNewUser(User user,
                                SuccessfulResponseWithResultCallback<User> resultCallback,
                                FailedResponseCallback failedCallback) {
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = auth.getCurrentUser().getUid();
                        user.setUserId(userId);
                        database.child(userId).setValue(user);
                        resultCallback.onSuccess(user);
                    }
                })
                .addOnFailureListener(e -> failedCallback.onError(e.getMessage()));
    }

}
