package com.ng.vkchallenge2017.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.sticker.Sticker;
import com.ng.vkchallenge2017.ui.adapter.StickerRVAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 17.09.17.
 */

public class StickerDialog extends Dialog {

    @BindView(R.id.sticker_text)
    TextView mStickerText;
    @BindView(R.id.stickers_recycler)
    RecyclerView mStickerRecycler;

    private StickerRVAdapter mStickerRVAdapter;

    public StickerDialog(@NonNull final Context context,
                         @NonNull final int themeResId,
                         @NonNull final List<Sticker> stickers,
                         @NonNull final StickerRVAdapter.StickerClickListener listener) {
        this(context, themeResId);

        mStickerRVAdapter = new StickerRVAdapter(getContext(), stickers);
        mStickerRVAdapter.setStickerClickListener(listener);
        initRecycler();
    }

    public StickerDialog(@NonNull final Context context, @NonNull final int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_stiker);

        ButterKnife.bind(this);

        setCancelable(true);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void initRecycler() {
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4, OrientationHelper.VERTICAL, false);
        mStickerRecycler.setLayoutManager(gridLayoutManager);
        mStickerRecycler.setAdapter(mStickerRVAdapter);
    }
}
