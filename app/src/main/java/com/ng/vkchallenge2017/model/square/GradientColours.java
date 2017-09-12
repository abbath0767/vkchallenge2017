package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public class GradientColours {
    @NonNull private final int startColour;
    @NonNull private final int endColour;

    public GradientColours(@NonNull final int startColour, @NonNull final int endColour) {
        this.startColour = startColour;
        this.endColour = endColour;
    }

    @NonNull
    public int getStartColour() {
        return startColour;
    }

    @NonNull
    public int getEndColour() {
        return endColour;
    }
}
