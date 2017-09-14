package com.ng.vkchallenge2017.model.photo;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 14.09.17.
 */

abstract public class PhotoSquareBase<T> {
    @NonNull private final PhotoSquareType type;

    public PhotoSquareBase(@NonNull final PhotoSquareType type) {
        this.type = type;
    }

    public abstract T getResource();

    @NonNull
    public PhotoSquareType getType() {
        return type;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PhotoSquareBase<?> that = (PhotoSquareBase<?>) o;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
