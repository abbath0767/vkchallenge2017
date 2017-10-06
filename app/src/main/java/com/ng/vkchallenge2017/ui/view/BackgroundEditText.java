package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.ng.vkchallenge2017.R;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 05.10.17.
 */

public class BackgroundEditText extends View {

    private WeakReference<TextView> mTextViewWeak;

    private int lnum;
    private int[] points;
    private Rect rect;
    private Paint paint;
    private int left, right, top, bot, start, end, width, crop;
    private int padding;
    private CornerPathEffect mCornerPathEffect;
    private Path mPath;

    private Paint helpPaint;

    public BackgroundEditText(final Context context) {
        super(context);
    }

    public void init() {
        if (helpPaint.getColor() == getContext().getResources().getColor(android.R.color.transparent)) {
            return;
        }
        rect = new Rect();
        if (paint == null)
            paint = mTextViewWeak.get().getPaint();
        if (helpPaint != null) {
            helpPaint.setPathEffect(mCornerPathEffect);
            helpPaint.setStrokeWidth(4);
            helpPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            helpPaint.setAntiAlias(true);
        }
        if (mPath == null) {
            mPath = new Path();
        }
        lnum = mTextViewWeak.get().getLineCount();
//        Timber.i("init. paint colour: %s lnum: %d", paint.toString(), lnum);
        points = new int[lnum * 4];
        initPoints();
    }

    //todo simplest this! (mb move this in another class)
    private void initPoints() {
        for (int i = 0; i < lnum; i++) {
            Timber.i("initPoints. line %d intL %d", i, mTextViewWeak.get().getLineBounds(i, rect));
            start = mTextViewWeak.get().getLayout().getLineStart(i);
            end = mTextViewWeak.get().getLayout().getLineEnd(i);
            left = rect.left;
            right = rect.right;
            top = rect.top;
            bot = rect.bottom;
            width = Math.round(paint.measureText(mTextViewWeak.get().getText(), start, end));
            crop = (right - width) / 2;
//            Timber.i("initPoints l %d, r %d, t %d, b %d", left, right, top, bot);
//            Timber.i("initPoints start: %d, end: %d", start, end);
//            Timber.i("initPoints text width: %d", width);
            //left border
            points[0 + i * 4] = left + crop - padding;
            //top
            points[1 + i * 4] = top;
            //right
            points[2 + i * 4] = right - crop + padding;
            //bot
            points[3 + i * 4] = bot;
        }

        Timber.i("initPoints. init finish, array: %s", Arrays.toString(points));
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (helpPaint.getColor() == getContext().getResources().getColor(android.R.color.transparent)) {
            return;
        }
        if (mTextViewWeak.get().getText().length() > 0) {
            mPath.reset();
            mPath.moveTo(points[2], points[1]);
            for (int i = 0; i < lnum; i++) {
                if (i !=0)
                    mPath.lineTo(points[2 + i * 4], points[1 + i * 4]);

                mPath.lineTo(points[2 + i * 4], points[3 + i * 4]);
            }

            mPath.lineTo(points[0 + (lnum - 1) * 4], points[3 + (lnum - 1) * 4]);

            for (int i = lnum - 1; i >= 0; i--) {
                if (i != lnum - 1) {
                    mPath.lineTo(points[0 + i * 4], points[3 + i * 4]);
                }
                mPath.lineTo(points[0 + i * 4], points[1 + i * 4]);
            }

            mPath.close();
            canvas.drawPath(mPath, helpPaint);
        }
    }

    public static class Builder {
        private BackgroundEditText mBackgroundEditText;

        public Builder context(@NonNull final Context context) {
            this.mBackgroundEditText = new BackgroundEditText(context);
            return this;
        }

        public Builder forText(@NonNull final TextView textView) {
            this.mBackgroundEditText.mTextViewWeak = new WeakReference<TextView>(textView);
            return this;
        }

        public Builder withColor(final int color) {
            this.mBackgroundEditText.helpPaint = new Paint();
            this.mBackgroundEditText.helpPaint.setColor(color);
            return this;
        }

        public Builder withRadius(final int radius) {
            this.mBackgroundEditText.mCornerPathEffect = new CornerPathEffect(radius);
            return this;
        }

        public Builder withHorizontalPadding(final int padding) {
            this.mBackgroundEditText.padding = padding;
            return this;
        }

        public BackgroundEditText build() {
            mBackgroundEditText.init();
            return mBackgroundEditText;
        }
    }
}
