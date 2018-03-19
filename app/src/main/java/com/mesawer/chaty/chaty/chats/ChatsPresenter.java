package com.mesawer.chaty.chaty.chats;

import com.mesawer.chaty.chaty.chats.model.ChatsDataSource;
import com.mesawer.chaty.chaty.data.User;

import io.reactivex.Observable;

/**
 * Created by ilias on 15/03/2018.
 */

public class ChatsPresenter implements ChatsContract.Presenter {

    private ChatsContract.View chatsView;
    private ChatsDataSource chatsDataSource;

    public ChatsPresenter(ChatsContract.View chatsView, ChatsDataSource chatsDataSource) {
        this.chatsView = chatsView;
        this.chatsDataSource = chatsDataSource;
    }

    @Override
    public void getChats(User user) {
        if (!user.getChats().isEmpty())
            chatsView.showLoadingIndicator();
        chatsDataSource.getChats(user)
                .subscribe(
                        chats -> {
                            chatsView.hideLoadingIndicator();
                            chatsView.showChats(chats);
                        },
                        throwable -> {
                            chatsView.hideLoadingIndicator();
                            chatsView.showErrorMessage(throwable.getMessage());
                        });
    }
}
