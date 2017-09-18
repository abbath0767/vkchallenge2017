package com.ng.vkchallenge2017.Util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.LineBackgroundSpan;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 16.09.17.
 */

public class SpanColoredBackground implements LineBackgroundSpan {
    private int mBackgroundColor;
    private int mPadding;
    private int mCornerRadius;
    private RectF mBgRect;

    public SpanColoredBackground(int backgroundColor, int padding, int cornerRadius) {
        super();
        mBackgroundColor = backgroundColor;
        mPadding = padding;
        mBgRect = new RectF();
        mCornerRadius = cornerRadius;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        final int textWidth = Math.round(p.measureText(text, start, end));
        int crop = (right - textWidth) / 2;
        final int paintColor = p.getColor();

        mBgRect.set(left + crop - mPadding,
                top - (lnum == 0 ? mPadding / 2 : - (mPadding / 2)),
                right - crop + mPadding,
                bottom + mPadding/2);
        p.setColor(mBackgroundColor);
        c.drawRoundRect(mBgRect, mCornerRadius, mCornerRadius, p);
        p.setColor(paintColor);
    }
}
