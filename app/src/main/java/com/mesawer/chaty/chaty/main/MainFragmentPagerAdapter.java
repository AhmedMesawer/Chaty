package com.mesawer.chaty.chaty.main;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mesawer.chaty.chaty.R;
import com.mesawer.chaty.chaty.chats.ChatsFragment;
import com.mesawer.chaty.chaty.friends.FriendsFragment;
import com.mesawer.chaty.chaty.friendship_requests.FriendshipRequestsFragment;

/**
 * Created by ilias on 07/03/2018.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] imageResId = {
            R.drawable.ic_chats,
            R.drawable.ic_friends,
            R.drawable.ic_notification};
    private int[] imageDisableResId = {
            R.drawable.ic_chats_disable,
            R.drawable.ic_friends_disable,
            R.drawable.ic_notification_disable};


    public MainFragmentPagerAdapter(Context context, FragmentManager fm, TabLayout tabLayout, ViewPager viewPager) {
        super(fm);
        this.context = context;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
        setupTabLayout();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChatsFragment();
            case 1:
                return new FriendsFragment();
            case 2:
                return new FriendshipRequestsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chats";
            case 1:
                return "Friends";
            case 2:
                return "Requests";
            default:
                return null;
        }
    }

    private void setupTabLayout() {
        viewPager.setAdapter(this);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < imageResId.length; i++) {
            if (tabLayout.getTabAt(i).isSelected()) {
                tabLayout.getTabAt(i).setIcon(imageResId[i]);
            } else {
                tabLayout.getTabAt(i).setIcon(imageDisableResId[i]);
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.ic_chats);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_friends);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_notification);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.ic_chats_disable);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_friends_disable);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_notification_disable);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
