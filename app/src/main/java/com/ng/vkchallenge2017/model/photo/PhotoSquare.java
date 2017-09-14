package com.ng.vkchallenge2017.model.photo;

import android.support.annotation.NonNull;

/**
 * Created by nikitagusarov on 14.09.17.
 */

public class PhotoSquare extends PhotoSquareBase<String>{

    @NonNull private final  String photoPath;

    public PhotoSquare(@NonNull final String photoPath) {
        super(PhotoSquareType.PHOTO);
        this.photoPath = photoPath;
    }

    @Override
    public String getResource() {
        return photoPath;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PhotoSquare that = (PhotoSquare) o;

        return photoPath.equals(that.photoPath);
    }

    @Override
    public int hashCode() {
        return photoPath.hashCode();
    }
}
