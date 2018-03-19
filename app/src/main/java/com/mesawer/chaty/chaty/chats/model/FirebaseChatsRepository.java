package com.mesawer.chaty.chaty.chats.model;

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

import io.reactivex.Maybe;

/**
 * Created by ilias on 08/03/2018.
 */

public class FirebaseChatsRepository implements ChatsDataSource {

    private static FirebaseChatsRepository INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private FirebaseChatsRepository() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference().child("users");
    }

    public static FirebaseChatsRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseChatsRepository();
        return INSTANCE;
    }

    @Override
    public Maybe<List<User>> getChats(User user) {

        return Maybe.create(emitter -> {
            List<User> friends = new ArrayList<>();
            database.child(user.getUserId()).child("chats")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot chatsSnapshot, String s) {
                            friends.clear();
                            database.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot usersSnapshot) {
                                    for (DataSnapshot snapshot : usersSnapshot.getChildren()){
                                        if (snapshot.getKey().equals(chatsSnapshot.getKey())){
                                            User friend = snapshot.getValue(User.class);
                                            friend.setChat_id(chatsSnapshot.getValue(String.class));
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
    }
}
