package com.mesawer.chaty.chaty.base;

/**
 * Created by ilias on 21/02/2018.
 */

public interface IView {
    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showErrorMessage(String errMsg);
}
