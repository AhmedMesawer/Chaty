package com.mesawer.chaty.chaty.registration;

import com.mesawer.chaty.chaty.base.IView;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 05/03/2018.
 */

public interface RegistrationContract {

    interface View extends IView {

        void navigateToMainActivity(User user);
    }

    interface Presenter {
        void registerNewUser(User user);
    }
}
