package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public class BottomSquareDefault extends BottomSquareBase implements Resourcable<Integer> {

    //standart square have only one colour
    @NonNull private int mResource;

    public BottomSquareDefault(@NonNull final int colour) {
        super(BottomSquareType.DEFAULT);
        mResource = colour;
    }

    @NonNull
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

        final BottomSquareDefault that = (BottomSquareDefault) o;

        return mResource == that.mResource;
    }

    @Override
    public int hashCode() {
        return mResource;
    }
}
