package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class BottomSquareRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private Context mContext;
    @Nullable
    private List<BottomSquareBase> mSquares;

    BottomSquareRVAdapter(@NonNull final Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bottom_bar_square, parent, false);

        return new SquareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (mSquares != null)
            ((SquareViewHolder) holder).bindSquare(mSquares.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        if (mSquares != null)
            return mSquares.size();
        else
            return 0;
    }

    public void setSquares(@NonNull final List<BottomSquareBase> squares) {
        Timber.i("setSquares. list: %s", Arrays.toString(squares.toArray()));
        mSquares = squares;
        notifyDataSetChanged();
    }

    public static class SquareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.bottom_bar_square)
        ImageView mImageView;

        public SquareViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {
            Timber.i("onClick on square!!! position %d", getAdapterPosition());
        }

        public void bindSquare(@NonNull final BottomSquareBase square, @NonNull final Context context) {
            Timber.i("bindSquare. type %s", square.getClass().getSimpleName());
            switch (square.getType()) {
                case DEFAULT:
                case ACTION:
                case THUMB: {
                    mImageView.setImageResource((int) square.getResource());
                    break;
                }
                case COLOR: {
                    mImageView.setBackgroundDrawable(
                            GradientDrawableFactory.getGradient(
                                    ((GradientColours) square.getResource()).getStartColour(),
                                    ((GradientColours) square.getResource()).getEndColour(),
                                    GradientDrawable.Orientation.TL_BR,
                                    context));
                    break;
                }
            }
        }
    }
}
