package com.mesawer.chaty.chaty.chatting.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mesawer.chaty.chaty.data.Chat;
import com.mesawer.chaty.chaty.data.Message;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by ilias on 08/03/2018.
 */

public class FirebaseChattingRepository implements ChattingDataSource {

    private static FirebaseChattingRepository INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;

    private FirebaseChattingRepository() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference();
    }

    public static FirebaseChattingRepository getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseChattingRepository();
        return INSTANCE;
    }

    @Override
    public Observable<Message> getMessages(User current, User friend) {

        return Observable.create(emitter -> {
            database.child("chats")
                    .child(current.getChat_id())
                    .child("messages")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot messagesSnapshot, String s) {
                            Message message = messagesSnapshot.getValue(Message.class);
                            emitter.onNext(message);
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
                            emitter.onError(databaseError.toException());
                        }
                    });
        });
    }

    @Override
    public void createChat(User current, User friend) {
        Chat chat = new Chat(current.getUserId(), friend.getUserId());
        database.child("chats").child(current.getUserId() + friend.getUserId())
                .setValue(chat)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        database.child("users")
                                .child(current.getUserId())
                                .child("chats")
                                .child(friend.getUserId())
                                .setValue(current.getUserId() + friend.getUserId());

                        database.child("users")
                                .child(friend.getUserId())
                                .child("chats")
                                .child(current.getUserId())
                                .setValue(current.getUserId() + friend.getUserId());
                    }
                })
                .addOnFailureListener(e -> Log.d("chat", e.getMessage()));
    }

    @Override
    public void newMessage(User current, User friend, Message message) {
        if (!isChatExist(current, friend)){
            database.child("users")
                    .child(current.getUserId())
                    .child("chats")
                    .child(friend.getUserId())
                    .setValue(current.getUserId() + friend.getUserId());

            database.child("users")
                    .child(friend.getUserId())
                    .child("chats")
                    .child(current.getUserId())
                    .setValue(current.getUserId() + friend.getUserId())
                    .addOnCompleteListener(
                            task -> {
                                current.getChats()
                            .put(friend.getUserId(), current.getUserId() + friend.getUserId());
                                current.setChat_id(current.getUserId() + friend.getUserId());
                            });
        }
        database.child("chats").child(current.getChat_id())
                .child("messages").push().setValue(message);
    }

    private boolean isChatExist(User current, User friend) {
        if (current.getChats().containsKey(friend.getUserId())) {
            current.setChat_id(current.getChats().get(friend.getUserId()));
            return true;
        }
        return false;
    }
}
