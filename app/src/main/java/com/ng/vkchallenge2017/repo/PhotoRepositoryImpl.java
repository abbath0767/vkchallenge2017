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

    @NonNull
    private final Context mContext;
    @NonNull
    private List<PhotoSquareBase> mPhotoSquareBases;

    public PhotoRepositoryImpl(@NonNull final Context context) {
        mContext = context;
        initList();
    }

    private void initList() {
        mPhotoSquareBases = new ArrayList<>();
        mPhotoSquareBases.add(new ActionSquare(R.drawable.ic_photopicker_camera));
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
        String bucket;
        String path;
        String id;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media._ID
        };

        cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        Timber.i("loadPhotoFromGallery. count: %d", cursor.getCount());
        int imageUriColumn = cursor.getColumnIndex(
                MediaStore.Images.Media.DATA);

        int i = 0;
        do {
            i++;
            path = cursor.getString(imageUriColumn);
            list.add(path);
        } while (cursor.moveToNext() && i < MAX_SQUARE_COUNT - 2);

        Timber.i("loadPhotoFromGallery size: %d", list.size());
        List<PhotoSquareBase> listPhotoSquares = new ArrayList<>(MAX_SQUARE_COUNT - 2);
        for (i = list.size() - 1; i >= 0; i--) {
            listPhotoSquares.add(new PhotoSquare(list.get(i)));
        }

        addPhotos(listPhotoSquares);
    }
}
