package com.ng.vkchallenge2017.model.photo;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 14.09.17.
 */

public class ActionSquare extends PhotoSquareBase<Integer>{

    @NonNull final int res;

    public ActionSquare(@NonNull final int res) {
        super(PhotoSquareType.ACTION);
        this.res = res;
    }

    @Override
    public Integer getResource() {
        return res;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ActionSquare that = (ActionSquare) o;

        return res == that.res;
    }

    @Override
    public int hashCode() {
        return res;
    }
}
