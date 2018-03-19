package com.mesawer.chaty.chaty.friendship_requests.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;

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
    public Maybe<List<User>> getFriendRequests(User user) {
        return Maybe.create(emitter -> {
            List<User> friendRequests = new ArrayList<>();
            database.child(user.getUserId()).child("incomingRequests")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot incomingSnapshot, String s) {
                            friendRequests.clear();
                            database.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot usersSnapshot) {
                                    for (DataSnapshot snapshot : usersSnapshot.getChildren()) {
                                        if (snapshot.getKey().equals(incomingSnapshot.getKey())) {
                                            friendRequests.add(snapshot.getValue(User.class));
                                        }
                                    }
                                    emitter.onSuccess(friendRequests);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    emitter.onError(databaseError.toException());
                                }
                            });
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {}

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            emitter.onError(databaseError.toException());
                        }
                    });
        });
    }

    @Override
    public Maybe<User> acceptFriendRequest(User current, User userToAccept) {
//        current.addOutgoingRequest(userToAccept.getUserId());
        return Maybe.create(
                emitter -> database.child(current.getUserId()).child("friends")
                        .child(userToAccept.getUserId())
                        .setValue(new Date().toString())
                        .addOnCompleteListener(
                                task -> {
                                    if (task.isSuccessful()) {
                                        //region add current as a friend
                                        database.child(userToAccept.getUserId()).child("friends")
                                                .child(current.getUserId())
                                                .setValue(new Date().toString())
                                                .addOnCompleteListener(
                                                        t -> {
                                                            if (t.isSuccessful()) {
                                                                database.child(current.getUserId())
                                                                        .child("incomingRequests")
                                                                        .child(userToAccept.getUserId())
                                                                        .removeValue();

                                                                database.child(userToAccept.getUserId())
                                                                        .child("outgoingRequests")
                                                                        .child(current.getUserId())
                                                                        .removeValue();
                                                                emitter.onSuccess(userToAccept);
                                                            }
                                                        });
                                        //endregion
                                    }
                                })
                        .addOnFailureListener(emitter::onError));
    }

    @Override
    public Maybe<User> ignoreFriendRequest(User current, User userToIgnore) {
        return Maybe.create(emitter -> {
            database.child(current.getUserId()).child("incomingRequests")
                    .child(userToIgnore.getUserId())
                    .removeValue();

            database.child(userToIgnore.getUserId()).child("outgoingRequests")
                    .child(current.getUserId())
                    .removeValue(
                            (databaseError, databaseReference) -> emitter.onSuccess(userToIgnore));
        });
    }
}
