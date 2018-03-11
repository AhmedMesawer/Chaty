package com.mesawer.chaty.chaty.add_friend.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.mesawer.chaty.chaty.base.FailedResponseCallback;
import com.mesawer.chaty.chaty.base.SuccessfulResponseWithResultCallback;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

/**
 * Created by ilias on 08/03/2018.
 */

public class FirebaseAddFriendsRepository implements AddFriendsDataSource {

    private static FirebaseAddFriendsRepository INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private FirebaseAddFriendsRepository() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference().child("users");
    }

    public static FirebaseAddFriendsRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseAddFriendsRepository();
        return INSTANCE;
    }

    @Override
    public void getUsers(User user,
                         SuccessfulResponseWithResultCallback<User> resultCallback,
                         FailedResponseCallback failedCallback) {
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User friend =dataSnapshot.getValue(User.class);
                resultCallback.onSuccess(friend);
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

    @Override
    public void sendFriendRequest(User current, User userToSend, SuccessfulResponseWithResultCallback<User> resultCallback, FailedResponseCallback failedCallback) {

    }
}
