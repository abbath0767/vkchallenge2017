package com.ng.vkchallenge2017.ui.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;
import com.ng.vkchallenge2017.ui.view.SquaredCardView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 14.09.17.
 */

public class PopupSquareAdapter extends RecyclerView.Adapter<PopupSquareAdapter.SquareViewHolder> {

    @NonNull
    private Context mContext;
    @NonNull
    private List<PhotoSquareBase> mSquares;
    @Nullable
    private SquareClickListener mSquareClickListener;

    private int selectedItem = 2;

    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }

    public PopupSquareAdapter(@NonNull final Context context) {
        mContext = context;
    }

    public void setSquareClickListener(@NonNull final SquareClickListener squareClickListener) {
        mSquareClickListener = squareClickListener;
    }

    @Override
    public SquareViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup_square, parent, false);

        return new SquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SquareViewHolder holder, final int position) {
        holder.bindView(mSquares.get(position), mContext);
        holder.setChecked(position == selectedItem);
    }

    @Override
    public int getItemCount() {
        return mSquares.size();
    }

    @Override
    public long getItemId(final int position) {
        return super.getItemId(position);
    }

    public void setSquares(@NonNull final List<PhotoSquareBase> squares) {
        mSquares = squares;
    }

    public interface SquareClickListener {
        void onSquareClick(int position);
    }

    public class SquareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.popup_square_card)
        SquaredCardView mCardView;
        @BindView(R.id.popup_image)
        ImageView mImageView;
        @BindView(R.id.popup_border_inner)
        View mBorderInner;
        @BindView(R.id.popup_border_outher)
        View mBorderOuther;

        public SquareViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            int pos = getLayoutPosition();
            selectedItem = pos;
            if (mSquareClickListener != null) {
                mSquareClickListener.onSquareClick(pos);
            }

            notifyDataSetChanged();
        }

        public void bindView(final PhotoSquareBase square, final Context context) {
            switch (square.getType()) {
                case ACTION: {
                    mImageView.setImageResource((int) square.getResource());
                    mImageView.setBackgroundColor(context.getResources().getColor(R.color.blue_button_transperent_24));
                    break;
                }
                case PHOTO: {
                    Glide.with(mContext)
                            .load((String)square.getResource())
                            .asBitmap()
                            .into(mImageView);
                }
            }
         }

        public void setChecked(final boolean checked) {
            mCardView.setSelected(checked);
            invalidateView(checked);
        }

        private void invalidateView(final boolean checked) {
            if (checked) {
                mBorderInner.bringToFront();
                mBorderOuther.setVisibility(View.VISIBLE);
                mBorderOuther.bringToFront();
                mBorderInner.requestLayout();
                mBorderOuther.requestLayout();
                mBorderInner.invalidate();
                mBorderOuther.invalidate();
            } else {
                mImageView.bringToFront();
                mImageView.requestLayout();
                mImageView.invalidate();
                mBorderOuther.setVisibility(View.GONE);
            }
        }
    }
}
