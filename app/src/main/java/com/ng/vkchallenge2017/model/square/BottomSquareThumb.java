package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public class BottomSquareThumb extends BottomSquareBase implements Resourcable<Integer> {

    @NonNull private int mResource;

    public BottomSquareThumb(@NonNull final int resourceId) {
        super(BottomSquareType.THUMB);
        mResource = resourceId;
    }

    @Override
    public Integer getResource() {
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

        return mResource == that.mResource;
    }

    @Override
    public int hashCode() {
        return mResource;
    }
}
