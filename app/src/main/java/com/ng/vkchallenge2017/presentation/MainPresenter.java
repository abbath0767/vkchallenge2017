package com.ng.vkchallenge2017.presentation;

import android.support.annotation.NonNull;

import com.ng.vkchallenge2017.view.MainViewContract;
import com.vk.sdk.VKSdk;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class MainPresenter implements MainViewContract.Presenter {

    @NonNull final MainViewContract.View mView;

    public MainPresenter(@NonNull final  MainViewContract.View view) {
        mView = view;

        if (VKSdk.isLoggedIn()) {
            showPostActivity();
        }
    }

    public void onLoginClick() {
        mView.makeLoginCall();
    }

    public void loginSuccess() {
        showPostActivity();
    }

    private void showPostActivity() {
        mView.showPostActivity();
    }
}
