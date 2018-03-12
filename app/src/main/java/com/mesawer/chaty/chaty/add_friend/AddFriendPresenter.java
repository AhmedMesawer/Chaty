package com.mesawer.chaty.chaty.add_friend;

import com.mesawer.chaty.chaty.add_friend.model.AddFriendsDataSource;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 11/03/2018.
 */

public class AddFriendPresenter implements AddFriendContract.Presenter {

    private AddFriendContract.View addFriendView;
    private AddFriendsDataSource addFriendsDataSource;

    public AddFriendPresenter(AddFriendContract.View addFriendView,
                              AddFriendsDataSource addFriendsDataSource) {
        this.addFriendView = addFriendView;
        this.addFriendsDataSource = addFriendsDataSource;
    }

    @Override
    public void getUsers(User current) {
        addFriendView.showLoadingIndicator();
        addFriendsDataSource.getUsers(current,
                result -> {
                    addFriendView.hideLoadingIndicator();
                    addFriendView.showUsers(result);
                },
                errMsg -> {
                    addFriendView.hideLoadingIndicator();
                    addFriendView.showErrorMessage(errMsg);
                });
    }

    @Override
    public void sendFriendRequest(User current, User userToSend) {
        addFriendsDataSource.sendFriendRequest(current, userToSend,
                result -> addFriendView.changeAddFriendButtonText(result),
                errMsg -> addFriendView.showErrorMessage(errMsg));
    }

    @Override
    public void cancelFriendRequest(User current, User userToCancel) {
        addFriendsDataSource.cancelFriendRequest(current, userToCancel,
                result -> addFriendView.changeAddFriendButtonText(result),
                errMsg -> addFriendView.showErrorMessage(errMsg));
    }
}
