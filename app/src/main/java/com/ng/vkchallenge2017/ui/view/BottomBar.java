package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.ui.adapter.BottomSquareRVAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class BottomBar extends ConstraintLayout {

    @BindView(R.id.bottom_bar_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.bottom_bar_send_button)
    Button mSentButton;

    private LayoutInflater mInflater;
    private BottomSquareRVAdapter mRVAdapter;

    public BottomBar(final Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public BottomBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public BottomBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        mInflater.inflate(R.layout.bottom_bar, this, true);
        ButterKnife.bind(this);
        mSentButton.setEnabled(false);

        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {

                    private static final float SPEED = 300f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        mRVAdapter = new BottomSquareRVAdapter(getContext());
        mRecyclerView.setAdapter(mRVAdapter);

        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setOnFlingListener(snapHelper);
    }

    public void setSquares(final List<BottomSquareBase> squares) {
        mRVAdapter.setSquares(squares);
    }

    public void setSquareClickListener(@NonNull final BottomSquareRVAdapter.SquareClickListener listener) {
        mRVAdapter.setSquareClickListener(listener);
    }

    public void setSentButtonEnabled(final boolean isEnabled) {
        mSentButton.setEnabled(isEnabled);
    }

    public void setSentButtonClickListener(final OnClickListener sentButtonClickListener) {
        mSentButton.setOnClickListener(sentButtonClickListener);
    }

    public void scrollTo(final int position) {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) mRecyclerView.getLayoutManager());
        int last = layoutManager.findLastVisibleItemPosition();
        int first = layoutManager.findFirstVisibleItemPosition();
        int visibleItems = last - first;
        Timber.i("scrollTo %d visible %d, first %d, last %d", position, visibleItems, first, last);
        if (Math.abs(position - first) < Math.abs(position - last)) {
            Timber.i("scrollTo LEFT");
            int pos = position - visibleItems / 2;
            if (pos >= 0)
                mRecyclerView.smoothScrollToPosition(pos);
            else
                mRecyclerView.smoothScrollToPosition(0);
        } else {
            Timber.i("scrollTo RIGHT");
            int pos = position + visibleItems / 2;
            int total = layoutManager.getItemCount();
            if (pos > total)
                mRecyclerView.smoothScrollToPosition(total);
            else if (pos == total) {
                mRecyclerView.smoothScrollToPosition(total);
            } else {
                mRecyclerView.smoothScrollToPosition(pos);
            }
        }
    }
}
