package com.ng.vkchallenge2017.repo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ng.vkchallenge2017.ui.activity.PostActivity;

/**
 * Created by nikitagusarov on 22.09.17.
 */

public class RepositoryComponent {

    public static StickerRepository getStickerRepository(@NonNull final Context context) {
        return StickerRepositoryImpl.newInstance(context);
    }

    public static SquareRepository getSquareRepository() {
        return SquareRepositoryImpl.getInstance();
    }

    public static TextStyleRepository getTextStyleRepository() {
        return TextStyleRepositoryImpl.getInstance();
    }

    public static PhotoRepository getPhotoRepository(final Context context) {
        return PhotoRepositoryImpl.getInstance(context);
    }
}
