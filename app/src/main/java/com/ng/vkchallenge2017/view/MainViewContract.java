package com.ng.vkchallenge2017.view;


/**
 * Created by nikitagusarov on 06.09.17.
 */

public interface MainViewContract {
    interface View{
        void makeLoginCall();

        void showPostActivity();
    }

    interface Presenter{

        void onLoginClick();

        void loginSuccess();
    }
}
