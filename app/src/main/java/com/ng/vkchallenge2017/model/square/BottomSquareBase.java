package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public abstract class BottomSquareBase<T> {

    @NonNull private BottomSquareType type;

    public BottomSquareBase(@NonNull final BottomSquareType type) {
        this.type = type;
    }

    public BottomSquareType getType() {
        return type;
    }

    public abstract T getResource();

    @Override
    public String toString() {
        return "BottomSquareBase{" +
                "type=" + type +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BottomSquareBase<?> that = (BottomSquareBase<?>) o;

        return type == that.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
