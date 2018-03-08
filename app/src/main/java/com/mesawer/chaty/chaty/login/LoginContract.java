package com.mesawer.chaty.chaty.login;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 06/03/2018.
 */

public interface LoginContract {

    interface View extends IView {
        void navigateToMainActivity(User user);
    }

    interface Presenter {
        void login(String email, String password);
    }
}
