package com.ng.vkchallenge2017.repo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.photo.ActionSquare;
import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitagusarov on 14.09.17.
 */

public class PhotoRepositoryImpl implements PhotoRepository{

    // 100 photo + 2 actions...
    public static final int MAX_SQUARE_COUNT = 102;

    @NonNull private final Context mContext;
    @NonNull private List<PhotoSquareBase> mPhotoSquareBases;

    public PhotoRepositoryImpl(@NonNull final Context context) {
        mContext = context;
        initList();
    }

    private void initList() {
        mPhotoSquareBases = new ArrayList<>();
        mPhotoSquareBases.add(new ActionSquare(R.drawable.ic_photopicker_camera));
        mPhotoSquareBases.add(new ActionSquare(R.drawable.ic_photopicker_albums));
        mPhotoSquareBases.add(new ActionSquare(R.drawable.ic_photopicker_albums));
        mPhotoSquareBases.add(new ActionSquare(R.drawable.ic_photopicker_albums));
    }

    @Override
    public List<PhotoSquareBase> getPhotoSquares() {
        return mPhotoSquareBases;
    }
}
