package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public abstract class BottomSquareBase<T> {

    @NonNull private BottomSquareType type;
    @NonNull private boolean isChecked = false;

    public BottomSquareBase(@NonNull final BottomSquareType type) {
        this.type = type;
    }

    public BottomSquareType getType() {
        return type;
    }

    @NonNull
    public boolean isChecked() {
        return isChecked;
    }

    public abstract T getResource();

    @Override
    public String toString() {
        return "BottomSquareBase{" +
                "type=" + type +
                ", isChecked=" + isChecked +
                '}';
    }
}
