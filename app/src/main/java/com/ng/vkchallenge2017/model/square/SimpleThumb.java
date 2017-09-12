package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 12.09.17.
 */

public class SimpleThumb extends Thumb<Integer> {
    @NonNull private final int resourceId;

    public SimpleThumb(final int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public Integer getResource() {
        return resourceId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SimpleThumb that = (SimpleThumb) o;

        return resourceId == that.resourceId;
    }

    @Override
    public int hashCode() {
        return resourceId;
    }
}
