package com.mesawer.chaty.chaty.friends;

import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.friends.model.FriendsDataSource;

/**
 * Created by ilias on 08/03/2018.
 */

public class FriendsPresenter implements FriendsContract.Presenter {

    private FriendsContract.View friendsView;
    private FriendsDataSource friendsDataSource;

    public FriendsPresenter(FriendsContract.View friendsView, FriendsDataSource friendsDataSource) {
        this.friendsView = friendsView;
        this.friendsDataSource = friendsDataSource;
    }

    @Override
    public void getFriends(User user) {
        friendsView.showLoadingIndicator();
        friendsDataSource.getFriends(user,
                result -> {
                    friendsView.hideLoadingIndicator();
                    friendsView.showFriends(result);
                },
                errMsg -> {
                    friendsView.hideLoadingIndicator();
                    friendsView.showErrorMessage(errMsg);
                });
    }
}
