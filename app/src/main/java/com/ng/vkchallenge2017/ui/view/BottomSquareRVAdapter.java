package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.Util.GradientDrawableFactory;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.GradientColours;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class BottomSquareRVAdapter extends RecyclerView.Adapter<BottomSquareRVAdapter.SquareViewHolder> {

    @NonNull
    private Context mContext;
    @Nullable
    private List<BottomSquareBase> mSquares;

    @Nullable
    private SquareClickListener mSquareClickListener;
    private int selectedItem = 0;

    BottomSquareRVAdapter(@NonNull final Context context) {
        mContext = context;
    }

    public void setSquareClickListener(final SquareClickListener squareClickListener) {
        mSquareClickListener = squareClickListener;
    }

    @Override
    public SquareViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bottom_bar_square, parent, false);

        return new SquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SquareViewHolder holder, final int position) {
        if (mSquares != null) {
            holder.bindSquare(mSquares.get(position), mContext);
            holder.setChecked(position == selectedItem);
        }
    }

    @Override
    public int getItemCount() {
        if (mSquares != null)
            return mSquares.size();
        else
            return 0;
    }

    @Override
    public long getItemId(final int position) {
        return super.getItemId(position);
    }

    public void setSquares(@NonNull final List<BottomSquareBase> squares) {
        mSquares = squares;
        notifyDataSetChanged();
    }

    public interface SquareClickListener{
        void onSquareClick(int position);
    }

    public class SquareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.bottom_bar_square)
        ImageView mImageView;
        @BindView(R.id.bottom_bar_card)
        CardView mCardView;
        @BindView(R.id.bottom_square_border_inner)
        View mBorderInner;
        @BindView(R.id.bottom_square_border_outher)
        View mBorderOuther;

        public SquareViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            int pos = getAdapterPosition();
            Timber.i("onClick on square! position %d", pos);
            selectedItem = pos;
            if (mSquareClickListener != null) {
                mSquareClickListener.onSquareClick(pos);
            }

            notifyDataSetChanged();

        }

        private void invalidateView(final boolean selected) {
            if (selected) {
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

        public void bindSquare(@NonNull final BottomSquareBase square, @NonNull final Context context) {
            switch (square.getType()) {
                case DEFAULT:
                case ACTION: {
                    mImageView.setImageResource((int) square.getResource());
                    mImageView.setBackgroundColor(context.getResources().getColor(R.color.blue_button_transperent_24));
                    break;
                }
                case THUMB: {
                    mImageView.setImageResource((int) square.getResource());
                    break;
                }
                case COLOR: {
                    mImageView.setBackground(
                            GradientDrawableFactory.getGradient(
                                    ((GradientColours) square.getResource()).getStartColour(),
                                    ((GradientColours) square.getResource()).getEndColour(),
                                    GradientDrawable.Orientation.TL_BR,
                                    context));
                    break;
                }
            }
        }

        public void setChecked(@NonNull final boolean checked) {
            mCardView.setSelected(checked);
            invalidateView(checked);
        }
    }
}
