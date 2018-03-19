package com.mesawer.chaty.chaty.profile;

import android.net.Uri;

import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.profile.model.ProfileDataSource;

import io.reactivex.Single;

/**
 * Created by ilias on 19/03/2018.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private ProfileContract.View profileView;
    private ProfileDataSource profileDataSource;

    public ProfilePresenter(ProfileContract.View profileView, ProfileDataSource profileDataSource) {
        this.profileView = profileView;
        this.profileDataSource = profileDataSource;
    }

    @Override
    public Single<User> getCurrentUser(String userId) {
        return profileDataSource.getCurrentUser(userId);
    }

    @Override
    public void uploadPhoto(Uri photoUri, String userId) {
        profileDataSource.uploadPhoto(photoUri, userId)
                .subscribe(profileView::changeProfilePhoto,
                        throwable -> profileView.showErrorMessage(throwable.getMessage()));
    }
}
