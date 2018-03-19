package com.mesawer.chaty.chaty.profile.model;

import android.net.Uri;

import com.mesawer.chaty.chaty.data.User;

import io.reactivex.Single;

/**
 * Created by ilias on 05/03/2018.
 */

public interface ProfileDataSource {

    Single<User> getCurrentUser(String userId);

    Single<String> uploadPhoto(Uri photoUri, String userId);
}
