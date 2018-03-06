package com.mesawer.chaty.chaty.utils;

import com.mesawer.chaty.chaty.login.model.FirebaseLoginDataSource;
import com.mesawer.chaty.chaty.registration.model.FirebaseRegistrationDataSource;

/**
 * Created by ilias on 24/02/2018.
 */

public class Injection {

    public static FirebaseRegistrationDataSource provideFirebaseRegistrationDataSource(){
        return FirebaseRegistrationDataSource.getInstance();
    }

    public static FirebaseLoginDataSource provideFirebaseLoginDataSource(){
        return FirebaseLoginDataSource.getInstance();
    }
}
