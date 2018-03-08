package com.mesawer.chaty.chaty.login;

import com.mesawer.chaty.chaty.login.model.LoginDataSource;

/**
 * Created by ilias on 06/03/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View loginView;
    private LoginDataSource loginDataSource;

    public LoginPresenter(LoginContract.View loginView, LoginDataSource loginDataSource) {
        this.loginView = loginView;
        this.loginDataSource = loginDataSource;
    }

    @Override
    public void login(String email, String password) {
        loginView.showLoadingIndicator();
        loginDataSource.login(email, password,
                result -> {
                    loginView.hideLoadingIndicator();
                    loginView.navigateToMainActivity(result);
                },
                errMsg -> {
                    loginView.hideLoadingIndicator();
                    loginView.showErrorMessage(errMsg);
                }
        );
    }
}
