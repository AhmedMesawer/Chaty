package com.mesawer.chaty.chaty.friends.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Emitter;
import io.reactivex.Maybe;

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
    public Maybe<List<User>> getFriends(User user) {

        return Maybe.create(emitter -> {
            List<User> friends = new ArrayList<>();
            database.child(user.getUserId()).child("friends")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot friendsSnapshot, String s) {
                            friends.clear();
                            database.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot usersSnapshot) {
                                    for (DataSnapshot snapshot : usersSnapshot.getChildren()){
                                        if (snapshot.getKey().equals(friendsSnapshot.getKey())){
                                            User friend = snapshot.getValue(User.class);
                                            friends.add(friend);
                                        }
                                    }
                                    emitter.onSuccess(friends);
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
//        database.child(user.getUserId()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("friends")){
//                    database.child(user.getUserId()).child("friends")
//                            .addChildEventListener(new ChildEventListener() {
//                                @Override
//                                public void onChildAdded(DataSnapshot friendsSnapshot, String s) {
//                                    database.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot usersSnapshot) {
//                                            for (DataSnapshot snapshot : usersSnapshot.getChildren()){
//                                                if (snapshot.getKey().equals(friendsSnapshot.getKey())){
//                                                    User friend = snapshot.getValue(User.class);
//                                                    resultCallback.onSuccess(friend);
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                            failedCallback.onError(databaseError.getMessage());
//                                        }
//                                    });
//                                }
//
//                                @Override
//                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
//
//                                @Override
//                                public void onChildRemoved(DataSnapshot dataSnapshot) {}
//
//                                @Override
//                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    failedCallback.onError(databaseError.getMessage());
//                                }
//                            });
//                }else {
//                    failedCallback.onError("no friends");
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
