package com.mesawer.chaty.chaty.profile.model;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.utils.Injection;

import io.reactivex.Single;

/**
 * Created by ilias on 05/03/2018.
 */

public class FirebaseProfileDataSource implements ProfileDataSource {

    private static FirebaseProfileDataSource INSTANCE;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private StorageReference storage;

    private FirebaseProfileDataSource() {
        auth = Injection.provideFirebaseAuth();
        database = Injection.provideFirebaseDatabaseReference().child("users");
        storage = Injection.providePhotosStorageReference();
    }

    public static FirebaseProfileDataSource getInstance() {
        if (INSTANCE == null) INSTANCE = new FirebaseProfileDataSource();

        return INSTANCE;
    }

    @Override
    public Single<User> getCurrentUser(String userId) {
        return Single.create(
                emitter -> database.child(userId)
                        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                emitter.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                emitter.onError(databaseError.toException());
            }
        }));
    }

    @Override
    public Single<String> uploadPhoto(Uri photoUri, String userId) {
        return Single.create(emitter -> {
            String photoName = photoUri.getLastPathSegment();
            storage.child(photoName)
                    .putFile(photoUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        String photoUrl = taskSnapshot.getDownloadUrl().toString();
                        database.child(userId).child("photoUrl")
                                .setValue(photoUrl);
                        emitter.onSuccess(photoUrl);
                    })
            .addOnFailureListener(emitter::onError);
        });
    }


    //TODO don't forget to remove any added listeners
}