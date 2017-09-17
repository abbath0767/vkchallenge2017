package com.ng.vkchallenge2017.model.sticker;

import android.support.annotation.NonNull;

import java.util.Arrays;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 17.09.17.
 */

public class Sticker {

    @NonNull final String absolutePath;

    public Sticker(@NonNull final String path) {
        this.absolutePath = path;
    }

    @NonNull
    public String getAbsolutePath() {
        return absolutePath;
    }

    public String getPath() {
        return absolutePath.split("/")[4] + "/" + absolutePath.split("/")[5];
    }
}
