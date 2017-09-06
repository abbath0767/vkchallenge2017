package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;


/**
 * Created by nikitagusarov on 07.09.17.
 */

public class BottomSquareColourGradient extends BottomSquareBase implements Resourcable<GradientColours> {

    @NonNull private GradientColours mResource;

    public BottomSquareColourGradient(@NonNull final int startColour, @NonNull final int endColour) {
        super(BottomSquareType.COLOR);
        mResource = new GradientColours(startColour, endColour);
    }

    @Override
    public GradientColours getResource() {
        return mResource;
    }

    @Override
    public String toString() {
        return "BottomSquareDefault{" +
                "mResource=" + mResource +
                "} BASE: " + super.toString() ;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BottomSquareColourGradient that = (BottomSquareColourGradient) o;

        return mResource.equals(that.mResource);
    }

    @Override
    public int hashCode() {
        return mResource.hashCode();
    }
}
