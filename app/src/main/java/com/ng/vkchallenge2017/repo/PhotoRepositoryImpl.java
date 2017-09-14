package com.ng.vkchallenge2017.repo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.os.CancellationSignal;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.photo.ActionSquare;
import com.ng.vkchallenge2017.model.photo.PhotoSquare;
import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 14.09.17.
 */

public class PhotoRepositoryImpl implements PhotoRepository {

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

    @Override
    public void addPhotos(final List<PhotoSquareBase> list) {
        mPhotoSquareBases.addAll(list);
    }

    @Override
    public void loadPhotoFromGallery() {
        Timber.i("loadPhotoFromGallery");
        Uri uri;
        List<String> list = new ArrayList<>(MAX_SQUARE_COUNT - 2);
        Cursor cursor;
        int columnIndex;
        String path;
        uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA};

        cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int i = 0;
        while(cursor.moveToNext()) {
            path = cursor.getString(columnIndex);
            Timber.i("loadPhotoFromGallery %d path: %s", i++, path);
            list.add(path);
        }

        Timber.i("loadPhotoFromGallery size: %d", list.size());
        List<PhotoSquareBase> listPhotoSquares = new ArrayList<>(MAX_SQUARE_COUNT - 2);
        for (String pathStr: list) {
            listPhotoSquares.add(new PhotoSquare(pathStr));
        }

        addPhotos(listPhotoSquares);
    }

}
