package com.mesawer.chaty.chaty.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mesawer.chaty.chaty.add_friend.model.FirebaseAddFriendsRepository;
import com.mesawer.chaty.chaty.friends.model.FirebaseFriendsRepository;
import com.mesawer.chaty.chaty.friendship_requests.model.FirebaseFriendRequestsRepository;
import com.mesawer.chaty.chaty.login.model.FirebaseLoginDataSource;
import com.mesawer.chaty.chaty.registration.model.FirebaseRegistrationDataSource;

/**
 * Created by ilias on 24/02/2018.
 */

public class Injection {

    public static FirebaseAuth provideFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference provideFirebaseDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseRegistrationDataSource provideFirebaseRegistrationDataSource(){
        return FirebaseRegistrationDataSource.getInstance();
    }

    public static FirebaseFriendRequestsRepository provideFirebaseFriendRequestsRepository(){
        return FirebaseFriendRequestsRepository.getInstance();
    }

    public static FirebaseAddFriendsRepository provideFirebaseAddFriendsRepository(){
        return FirebaseAddFriendsRepository.getInstance();
    }

    public static FirebaseFriendsRepository provideFirebaseFriendsRepository(){
        return FirebaseFriendsRepository.getInstance();
    }

    public static FirebaseLoginDataSource provideFirebaseLoginDataSource(){
        return FirebaseLoginDataSource.getInstance();
    }
}
