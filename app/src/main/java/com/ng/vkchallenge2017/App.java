package com.ng.vkchallenge2017;

import android.app.Application;

import com.vk.sdk.VKSdk;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
        initVkSdk();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initVkSdk() {
        VKSdk.initialize(getApplicationContext());
    }
}
