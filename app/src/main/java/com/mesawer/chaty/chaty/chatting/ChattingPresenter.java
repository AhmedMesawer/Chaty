package com.mesawer.chaty.chaty.chatting;

import com.mesawer.chaty.chaty.chatting.model.ChattingDataSource;
import com.mesawer.chaty.chaty.data.Message;
import com.mesawer.chaty.chaty.data.User;

/**
 * Created by ilias on 18/03/2018.
 */

public class ChattingPresenter implements ChattingContract.Presenter {

    private ChattingContract.View chattingView;
    private ChattingDataSource chattingDataSource;

    public ChattingPresenter(ChattingContract.View chattingView,
                             ChattingDataSource chattingDataSource) {
        this.chattingView = chattingView;
        this.chattingDataSource = chattingDataSource;
    }

    @Override
    public void getChat(User current, User friend) {
        if (isChatExist(current, friend)) {
            chattingView.showLoadingIndicator();
            chattingDataSource.getMessages(current, friend)
                    .subscribe(message -> {
                                chattingView.hideLoadingIndicator();
                                chattingView.showMessages(message);
                            },
                            throwable -> {
                                chattingView.hideLoadingIndicator();
                                chattingView.showErrorMessage(throwable.getMessage());
                            });
        } else {

            chattingDataSource.createChat(current, friend);
        }
    }

    @Override
    public void newMessage(User current, User friend, Message message) {
        chattingDataSource.newMessage(current, friend, message);
    }

    private boolean isChatExist(User current, User friend) {
        if (current.getChats().containsKey(friend.getUserId())) {
            current.setChat_id(current.getChats().get(friend.getUserId()));
            return true;
        }
        return false;
    }
}
