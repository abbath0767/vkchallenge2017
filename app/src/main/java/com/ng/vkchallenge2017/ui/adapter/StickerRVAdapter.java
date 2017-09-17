package com.ng.vkchallenge2017.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.sticker.Sticker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


/**
 * Created by nikitagusarov on 17.09.17.
 */

public class StickerRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Sticker> mStickers;

    public StickerRVAdapter(@NonNull final Context context, @NonNull final List<Sticker> stickers) {
        mContext = context;
        mStickers = stickers;
        Timber.i("StickerRVAdapter. stickers :%d", stickers.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sticker, parent, false);

        return new StickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        ((StickerViewHolder)holder).bindView(mContext, mStickers.get(position).getPath());
    }

    @Override
    public int getItemCount() {
        return mStickers.size();
    }

    public static class StickerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sticker_image)
        ImageView mImageView;

        public StickerViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bindView(Context context, String path) {
            Glide.with(context)
                    .load(path)
                    .asBitmap()
                    .fitCenter()
                    .into(mImageView);
        }
    }
}
