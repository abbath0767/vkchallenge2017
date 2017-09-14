package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 15.09.17.
 */

public class SquaredCardView extends CardView {
    public SquaredCardView(final Context context) {
        super(context);
    }

    public SquaredCardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
