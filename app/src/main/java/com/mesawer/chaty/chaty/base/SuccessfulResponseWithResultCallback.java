package com.mesawer.chaty.chaty.base;

/**
 * Created by ilias on 24/02/2018.
 */

public interface SuccessfulResponseWithResultCallback<T> {
    void onSuccess(T result);
}
