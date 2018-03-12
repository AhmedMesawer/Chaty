package com.mesawer.chaty.chaty.friendship_requests.model;

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

public class FirebaseFriendRequestsRepository implements FriendRequestsDataSource {

    private static FirebaseFriendRequestsRepository INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private FirebaseFriendRequestsRepository() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference().child("users");
    }

    public static FirebaseFriendRequestsRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseFriendRequestsRepository();
        return INSTANCE;
    }

    @Override
    public void getFriendRequests(User user,
                                  SuccessfulResponseWithResultCallback<User> resultCallback,
                                  FailedResponseCallback failedCallback) {
        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User friend = dataSnapshot.getValue(User.class);
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
    public void sendFriendRequest(User current, User userToSend,
                                  SuccessfulResponseWithResultCallback<User> resultCallback,
                                  FailedResponseCallback failedCallback) {
        current.addFriendRequest(userToSend.getUserId());
        int index = current.getFriendRequests() != null ?
                current.getFriendRequests().size() : 0;
        database.child(current.getUserId()).child("friendRequests")
                .child(String.valueOf(index))
                .setValue(userToSend.getUserId() )
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                resultCallback.onSuccess(userToSend);
                            }
                        })
                .addOnFailureListener(e -> failedCallback.onError(e.getMessage()));
    }
}
