package com.mesawer.chaty.chaty.main;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.data.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String USER_INTENT_KEY = "user";
    @BindView(R.id.main_tab_Layout)
    TabLayout mainTabLayout;
    @BindView(R.id.main_view_pager)
    ViewPager mainViewPager;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPagerAdapter();
        if (getIntent() != null) {
            user = getIntent().getParcelableExtra(USER_INTENT_KEY);
            if (user != null)
            Snackbar.make(mainLayout, user.getEmail(), Snackbar.LENGTH_LONG).show();
        }
    }

    private void initPagerAdapter() {
        new MainFragmentPagerAdapter(this,
                getSupportFragmentManager(),
                mainTabLayout, mainViewPager);
    }
}
