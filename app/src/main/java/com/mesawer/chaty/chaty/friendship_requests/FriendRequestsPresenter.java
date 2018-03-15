package com.mesawer.chaty.chaty.friendship_requests;

import com.mesawer.chaty.chaty.data.User;
import com.mesawer.chaty.chaty.friendship_requests.model.FriendRequestsDataSource;

/**
 * Created by ilias on 12/03/2018.
 */

public class FriendRequestsPresenter implements FriendRequestsContract.Presenter {

    private FriendRequestsContract.View friendRequestsView;
    private FriendRequestsDataSource friendRequestsDataSource;

    public FriendRequestsPresenter(FriendRequestsContract.View friendRequestsView,
                                   FriendRequestsDataSource friendRequestsDataSource) {
        this.friendRequestsView = friendRequestsView;
        this.friendRequestsDataSource = friendRequestsDataSource;
    }

    @Override
    public void getFriendRequests(User current) {
        if (!current.getIncomingRequests().isEmpty())
            friendRequestsView.showLoadingIndicator();
        friendRequestsDataSource.getFriendRequests(current)
                .subscribe(friendRequests -> {
                    friendRequestsView.hideLoadingIndicator();
                    friendRequestsView.showFriendRequests(friendRequests);
                }, throwable -> {
                    friendRequestsView.hideLoadingIndicator();
                    friendRequestsView.showErrorMessage(throwable.getMessage());
                });
    }

    @Override
    public void acceptFriendRequest(User current, User userToAccept) {
        friendRequestsDataSource.acceptFriendRequest(current, userToAccept)
                .subscribe(friendRequestsView::removeAcceptedRequestFromList,
                        throwable -> friendRequestsView.showErrorMessage(throwable.getMessage()));
    }

    @Override
    public void ignoreFriendRequest(User current, User userToIgnore) {
        friendRequestsDataSource.ignoreFriendRequest(current, userToIgnore)
                .subscribe(friendRequestsView::removeIgnoredRequestFromList,
                        throwable -> friendRequestsView.showErrorMessage(throwable.getMessage()));
    }
}
