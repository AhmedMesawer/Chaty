package com.mesawer.chaty.chaty.add_friend.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;

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
    public Maybe<List<User>> getUsers(User user) {
        return Maybe.create(emitter -> {
            List<User> users = new ArrayList<>();
            database.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    users.add(dataSnapshot.getValue(User.class));
                    emitter.onSuccess(users);
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
    public Maybe<User> sendFriendRequest(User current, User userToSend) {
        return Maybe.create(emitter -> {
            current.addOutgoingRequest(userToSend.getUserId());
            database.child(current.getUserId()).child("outgoingRequests")
                    .child(userToSend.getUserId())
                    .setValue(new Date().toString())
                    .addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    //region send request to user
                                    userToSend.addIncomingRequest(current.getUserId());
                                    database.child(userToSend.getUserId()).child("incomingRequests")
                                            .child(current.getUserId())
                                            .setValue(new Date().toString())
                                            .addOnCompleteListener(
                                                    t -> {
                                                        if (task.isSuccessful()) {
                                                            emitter.onSuccess(userToSend);
                                                        }
                                                    });
                                    //endregion
                                }
                            })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Maybe<User> cancelFriendRequest(User current, User userToSend) {
        return Maybe.create(emitter -> {
            current.removeOutgoingRequest(userToSend.getUserId());
            database.child(current.getUserId()).child("friendRequests")
                    .setValue(current.getOutgoingRequests())
                    .addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    emitter.onSuccess(userToSend);
                                }
                            })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
