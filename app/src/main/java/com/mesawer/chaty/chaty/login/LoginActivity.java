package com.mesawer.chaty.chaty.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.EditText;

import com.mesawer.chaty.chaty.BuildConfig;
import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.base.BaseActivity;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.main.MainActivity;
import com.mesawer.chaty.chaty.utils.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mesawer.chaty.chaty.main.MainActivity.CURRENT_USER_INTENT_KEY;
import static com.mesawer.chaty.chaty.utils.StringUtil.notNullOrEmpty;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    LoginContract.Presenter loginPresenter;
    @BindView(R.id.et_email_login)
    EditText etEmailLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.login_layout)
    ConstraintLayout loginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        super.view = loginLayout;
        loginPresenter = new LoginPresenter(this,
                Injection.provideFirebaseLoginDataSource());
        if (BuildConfig.DEBUG){
            etEmailLogin.setText("hamasa@gmail.com");
            etPassword.setText("123456");
        }
    }

    @Override
    public void navigateToMainActivity(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(CURRENT_USER_INTENT_KEY, user);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String email = etEmailLogin.getText().toString();
        String password = etPassword.getText().toString();
        if (notNullOrEmpty(email) && notNullOrEmpty(password))
            loginPresenter.login(email, password);
    }
}
