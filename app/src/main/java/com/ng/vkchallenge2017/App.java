package com.ng.vkchallenge2017;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
