package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public class BottomSquareThumb extends BottomSquareBase<Thumbs> {

    @NonNull private Thumbs mResource;

    public BottomSquareThumb(@NonNull final Thumbs resourceId) {
        super(BottomSquareType.THUMB);
        mResource = resourceId;
    }

    @Override
    public Thumbs getResource() {
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

        final BottomSquareThumb that = (BottomSquareThumb) o;

        return mResource.equals(that.mResource);
    }

    @Override
    public int hashCode() {
        return mResource.hashCode();
    }
}
