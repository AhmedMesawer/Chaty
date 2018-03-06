package com.mesawer.chaty.chaty.registration;

import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.registration.model.RegistrationDataSource;

/**
 * Created by ilias on 06/03/2018.
 */

public class RegistrationPresenter implements RegistrationContract.Presenter {

    private RegistrationContract.View registrationView;
    private RegistrationDataSource registrationDataSource;

    public RegistrationPresenter(RegistrationContract.View registrationView,
                                 RegistrationDataSource registrationDataSource) {
        this.registrationView = registrationView;
        this.registrationDataSource = registrationDataSource;
    }

    @Override
    public void registerNewUser(User user) {
        registrationView.showLoadingIndicator();
        registrationDataSource.registerNewUser(user,
                result -> {
                    registrationView.hideLoadingIndicator();
                    registrationView.navigateToMainActivity(result);
                },
                errMsg -> {
                    registrationView.hideLoadingIndicator();
                    registrationView.showErrorMessage(errMsg);
                });
    }
}
