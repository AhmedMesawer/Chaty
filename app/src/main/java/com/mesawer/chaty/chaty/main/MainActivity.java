package com.mesawer.chaty.chaty.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.profile.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mesawer.chaty.chaty.add_friend.AddFriendActivity.ADD_FRIEND_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_USER_INTENT_KEY = "currentUser";
    public static final String SHARED_PREFERENCES_FILE_KEY = "ChatySharedPreferencesFile";
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
            user = getIntent().getParcelableExtra(CURRENT_USER_INTENT_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_item:
                navigateToProfileActivity();
                return true;
            case R.id.sign_out_item:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(CURRENT_USER_INTENT_KEY, user.getUserId());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment friends = getSupportFragmentManager()
                .findFragmentByTag("android:switcher:" + R.id.main_view_pager + ":" +
                        mainViewPager.getCurrentItem());
        if (friends != null) {
            friends.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initPagerAdapter() {
        new MainFragmentPagerAdapter(this,
                getSupportFragmentManager(),
                mainTabLayout, mainViewPager);
    }
}
