package com.ng.vkchallenge2017;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
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
        initLeakCanary();
        initVkSdk();
    }

    private void initTimber() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initLeakCanary() {
        LeakCanary.install(this);
    }

    private void initVkSdk() {
        VKSdk.initialize(getApplicationContext());
    }
}
