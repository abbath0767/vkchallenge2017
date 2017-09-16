package com.ng.vkchallenge2017.Util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 16.09.17.
 */

public class BackgroundColorSpanMy implements LineBackgroundSpan {
    private int mBackgroundColor;
    private int mPadding;
    private RectF mBgRect;

    public BackgroundColorSpanMy(int backgroundColor, int padding) {
        super();
        mBackgroundColor = backgroundColor;
        mPadding = padding;
        // Precreate rect for performance
        mBgRect = new RectF();
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Timber.i("drawBackground, left: %d, right: %d, top: %d, baseLine: %d, bottom: %d \nstart: %d, end: %d, lnum: %d",
                left, right, top, baseline, bottom, start, end, lnum);
        final int textWidth = Math.round(p.measureText(text, start, end));
        Timber.i("drawBackground width: %d", textWidth);
        int crop = (right - textWidth) / 2;
        final int paintColor = p.getColor();

        mBgRect.set(left + crop - mPadding,
                top - (lnum == 0 ? mPadding / 2 : - (mPadding / 2)),
                right - crop + mPadding,
                bottom + mPadding - 2);
        p.setColor(mBackgroundColor);
        c.drawRoundRect(mBgRect, 10, 10, p);
        p.setColor(paintColor);
    }
}
