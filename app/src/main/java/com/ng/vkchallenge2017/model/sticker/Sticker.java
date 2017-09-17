package com.ng.vkchallenge2017.model.sticker;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 17.09.17.
 */

public class Sticker {

    @NonNull final String path;

    public Sticker(@NonNull final String path) {
        this.path = path;
    }

    @NonNull
    public String getPath() {
        return path;
    }
}
