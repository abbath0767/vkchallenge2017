package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;

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


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        mRVAdapter = new BottomSquareRVAdapter(getContext());
        mRecyclerView.setAdapter(mRVAdapter);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setSquares(final List<BottomSquareBase> squares) {
        mRVAdapter.setSquares(squares);
    }

    public void setSquareClickListener(@NonNull final BottomSquareRVAdapter.SquareClickListener listener) {
        mRVAdapter.setSquareClickListener(listener);
    }
}
