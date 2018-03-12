package com.mesawer.chaty.chaty.login.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

/**
 * Created by ilias on 05/03/2018.
 */

public class FirebaseLoginDataSource implements LoginDataSource {

    private static FirebaseLoginDataSource INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private FirebaseLoginDataSource() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference().child("users");
    }

    public static FirebaseLoginDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseLoginDataSource();

        return INSTANCE;
    }

    @Override
    public void login(String email, String password,
                      SuccessfulResponseWithResultCallback<User> resultCallback,
                      FailedResponseCallback failedCallback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        database.child(firebaseUser.getUid())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);
                                        resultCallback.onSuccess(user);
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        failedCallback.onError(databaseError.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(e -> failedCallback.onError(e.getMessage()));
    }

    //TODO don't forget to remove any added listeners
}