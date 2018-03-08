package com.mesawer.chaty.chaty.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.EditText;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseActivity;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.main.MainActivity;
import com.mesawer.chaty.chaty.utils.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mesawer.chaty.chaty.main.MainActivity.USER_INTENT_KEY;
import static com.mesawer.chaty.chaty.utils.StringUtil.isNullOrEmpty;
import static com.mesawer.chaty.chaty.utils.StringUtil.isValidEmailAddress;
import static com.mesawer.chaty.chaty.utils.StringUtil.notNullOrEmpty;

public class RegistrationActivity extends BaseActivity implements RegistrationContract.View {

    RegistrationContract.Presenter registrationPresenter;
    @BindView(R.id.et_registration_email)
    EditText etRegistrationEmail;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.et_registration_user_name)
    EditText etRegistrationUserName;
    @BindView(R.id.et_registration_password)
    EditText etRegistrationPassword;
    @BindView(R.id.et_registration_re_password)
    EditText etRegistrationRePassword;
    @BindView(R.id.registration_layout)
    ConstraintLayout registrationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        super.view = registrationLayout;
        registrationPresenter = new RegistrationPresenter(this,
                Injection.provideFirebaseRegistrationDataSource());
    }

    @Override
    public void navigateToMainActivity(User user) {
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.putExtra(USER_INTENT_KEY, user);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_sign_up)
    public void onViewClicked() {
        User user = createNewUser();
        if (user != null)
            registrationPresenter.registerNewUser(user);
    }

    //region Helper Methods
    private User createNewUser() {
        User user = null;
        String userName = etRegistrationUserName.getText().toString();
        String email = etRegistrationEmail.getText().toString();
        String password = etRegistrationPassword.getText().toString();
        String rePassword = etRegistrationRePassword.getText().toString();
        if (notNullOrEmpty(userName) && isValidEmailAddress(email) && notNullOrEmpty(password) &&
                password.length() >= 6 && password.equals(rePassword))
            user = new User(userName, email, password);
        if (isNullOrEmpty(userName))
            etRegistrationUserName.setError("User name should not be blank");
        if (!isValidEmailAddress(email))
            etRegistrationEmail.setError("Invalid Email Address");
        if (isNullOrEmpty(password) || password.length() < 6)
            etRegistrationPassword.setError("Enter password at least 6 characters");
        if (!password.equals(rePassword))
            etRegistrationRePassword.setError("password mismatching");

        return user;
    }
    //endregion
}

