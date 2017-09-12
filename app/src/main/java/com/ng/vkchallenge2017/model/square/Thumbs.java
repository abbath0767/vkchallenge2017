package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 12.09.17.
 */

public class Thumbs  {
    @NonNull private final int smallThumb;
    @NonNull private final Thumb bigThumb;

    public Thumbs(@NonNull final int smallThumb, @NonNull final Thumb bigThumb) {
        this.smallThumb = smallThumb;
        this.bigThumb = bigThumb;
    }

    @NonNull
    public int getSmallThumb() {
        return smallThumb;
    }

    @NonNull
    public Thumb getBigThumb() {
        return bigThumb;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Thumbs thumbs = (Thumbs) o;

        if (smallThumb != thumbs.smallThumb) return false;
        return bigThumb.equals(thumbs.bigThumb);
    }

    @Override
    public int hashCode() {
        int result = smallThumb;
        result = 31 * result + bigThumb.hashCode();
        return result;
    }
}
