package com.mesawer.chaty.chaty.profile;

import android.net.Uri;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

import io.reactivex.Single;

/**
 * Created by ilias on 06/03/2018.
 */

public interface ProfileContract {

    interface View extends IView{
        void changeProfilePhoto(String photoUrl);
    }

    interface Presenter {
        Single<User> getCurrentUser(String userId);

        void uploadPhoto(Uri photoUri, String userId);
    }
}
