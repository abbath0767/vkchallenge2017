package com.ng.vkchallenge2017.model.text_style;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 16.09.17.
 */

public class TextStyle {
    @NonNull final int backgroundColour;
    @NonNull final int textColour;

    public TextStyle(@NonNull final int background, @NonNull final int textColour) {
        this.backgroundColour = background;
        this.textColour = textColour;
    }

    @NonNull
    public int getBackgroundColour() {
        return backgroundColour;
    }

    @NonNull
    public int getTextColour() {
        return textColour;
    }
}
