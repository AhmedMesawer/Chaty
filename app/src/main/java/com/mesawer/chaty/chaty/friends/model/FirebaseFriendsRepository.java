package com.mesawer.chaty.chaty.friends.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

/**
 * Created by ilias on 08/03/2018.
 */

public class FirebaseFriendsRepository implements FriendsDataSource {

    private static FirebaseFriendsRepository INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private FirebaseFriendsRepository() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference().child("users");
    }

    public static FirebaseFriendsRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseFriendsRepository();
        return INSTANCE;
    }

    @Override
    public void getFriends(User user,
                           SuccessfulResponseWithResultCallback<User> resultCallback,
                           FailedResponseCallback failedCallback) {
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (user.getFriends() != null && !user.getFriends().isEmpty()) {
                        for (String key : user.getFriends()) {
                            if (key.equals(snapshot.getKey())){
                                User friend = snapshot.getValue(User.class);
                                resultCallback.onSuccess(friend);
                            }
                        }
                    } else {
                       failedCallback.onError("no friends");
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User friend =dataSnapshot.getValue(User.class);
//                resultCallback.onSuccess(friend);
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    for (String key : user.getUsers()) {
//                        if (key.equals(snapshot.getKey())){
//                            User friend = snapshot.getValue(User.class);
//                            resultCallback.onSuccess(friend);
//                        }
//                    }
//                }
//            }

//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                failedCallback.onError(databaseError.getMessage());
//            }
//        });
    }
}
