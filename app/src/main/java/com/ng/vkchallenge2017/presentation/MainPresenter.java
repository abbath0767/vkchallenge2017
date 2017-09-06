package com.ng.vkchallenge2017.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ng.vkchallenge2017.view.MainView;
import com.vk.sdk.VKSdk;

/**
 * Created by nikitagusarov on 06.09.17.
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Override
    public void attachView(final MainView view) {
        super.attachView(view);

        if (VKSdk.isLoggedIn()) {
            showPostActivity();
        }
    }

    public void onLoginClick() {
        getViewState().makeLoginCall();
    }

    public void loginSuccess() {
        showPostActivity();
    }

    private void showPostActivity() {
        getViewState().showPostActivity();
    }
}
