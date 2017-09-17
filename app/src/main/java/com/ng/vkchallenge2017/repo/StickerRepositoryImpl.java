package com.ng.vkchallenge2017.repo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ng.vkchallenge2017.model.sticker.Sticker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 17.09.17.
 */

public class StickerRepositoryImpl implements StickerRepository {

    private static final String STICKER_ASSET_FOLDER = "stickers";
    private static final String DIVIDER = "/";
    private static final String ASSET_PREFIX = "file:///android_asset/";
    private static final String PNG = ".png";

    private static StickerRepository instance;

    private Context mContext;

    public static StickerRepository newInstance(@NonNull final Context context) {
        if (instance == null)
            instance = new StickerRepositoryImpl(context);
        return instance;
    }

    private List<Sticker> mStickers;

    private StickerRepositoryImpl(@NonNull final Context context) {
        mContext = context;
        mStickers = new ArrayList<>();

        initList();
    }

    private void initList() {
        try {
            String[] list = mContext.getAssets().list(STICKER_ASSET_FOLDER);

            for (String path : list) {
                mStickers.add(new Sticker(ASSET_PREFIX + STICKER_ASSET_FOLDER + DIVIDER + path));
            }

            Collections.sort(mStickers, new Comparator<Sticker>() {
                @Override
                public int compare(final Sticker sticker, final Sticker t1) {
                    return Integer.valueOf(sticker.getAbsolutePath().split("_")[2].replace(PNG, ""))
                            .compareTo(Integer.valueOf(t1.getAbsolutePath().split("_")[2].replace(PNG, "")));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Sticker> getStickers() {
        return mStickers;
    }

    @Override
    public Sticker getSticker(final int position) {
        return mStickers.get(position);
    }
}
