package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.Util.SpanColoredBackground;
import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;
import com.ng.vkchallenge2017.model.sticker.Sticker;
import com.ng.vkchallenge2017.model.text_style.TextStyle;
import com.ng.vkchallenge2017.presentation.PostPresenter;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.StickerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.xiaopo.flying.sticker.BitmapStickerIcon.LEFT_TOP;

/**
 * Created by nikitagusarov on 11.09.17.
 */

public class CustomImageView extends RelativeLayout {

    @BindView(R.id.post_edit_text)
    EditText mPostEditText;
    @BindView(R.id.image_view_top)
    ImageView mImageViewTop;
    @BindView(R.id.image_view_mid)
    ImageView mImageViewMid;
    @BindView(R.id.image_view_bot)
    ImageView mImageViewBot;
    @BindView(R.id.sticker_container)
    StickerView mStickerView;

    private Context mContext;

    private LayoutInflater mInflater;
    private PostPresenter.Mode mMode = PostPresenter.Mode.POST;

    private BitmapStickerIcon cachedSticker;
    private Matrix cachedMatrix;

    public CustomImageView(final Context context) {
        super(context);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public CustomImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public CustomImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        mInflater.inflate(R.layout.image_collection, this, true);
        ButterKnife.bind(this);

        mStickerView.setConstrained(true);

        mStickerView.bringToFront();

        mPostEditText.bringToFront();
        mPostEditText.requestLayout();
    }

    public void setOnTextChangeListener(@NonNull final TextWatcher textWatcher) {
        mPostEditText.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        int rest = calculateRest();
        int overWidth = getMeasuredWidth();
//        Timber.i("onMeasure specs W: %d, H: %d", widthMeasureSpec, heightMeasureSpec);
//        Timber.i("onMeasure get() W: %d, H: %d", getMeasuredWidth(), getMeasuredHeight());
//        Timber.i("onMeasure getSpec() W: %d, H: %d", MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        int exceptedHeight = MeasureSpec.getSize(widthMeasureSpec);
        int realHeight = MeasureSpec.getSize(heightMeasureSpec);

//        Timber.i("onMeasure. excepted: %d, real: %d, rest: %d", exceptedHeight, realHeight, rest);
        if (mMode == PostPresenter.Mode.POST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            if (rest > exceptedHeight) {
//                Timber.i("onMeasure. need SQUARABLE");
                super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            } else {
//                Timber.i("onMeasure.DONT NEED");
//                Timber.i("onMeasure rest: %d", rest);

                int spec = MeasureSpec.makeMeasureSpec(rest, MeasureSpec.EXACTLY);
//                Timber.i("onMeasure. restSpex: %d", spec);
                setMeasuredDimension(exceptedHeight, rest);
                super.onMeasure(widthMeasureSpec, spec);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void clearFocus() {
        mPostEditText.clearFocus();
    }

    private int calculateRest() {
        int overHeight = 0;
        overHeight += ((ConstraintLayout) getParent()).getHeight();
//        Timber.i("calculateRest all H: %d", overHeight);
        for (int i = 0; i < ((ConstraintLayout) getParent()).getChildCount(); i++) {
//            Timber.i("calculateRest: %s", ((ConstraintLayout) getParent()).getChildAt(i));
            int minusHeight = 0;
            if (((ConstraintLayout) getParent()).getChildAt(i) instanceof TabLayout) {
                minusHeight = (((ConstraintLayout) getParent()).getChildAt(i)).getHeight();
                overHeight -= minusHeight;
//                Timber.i("calculateRest minus Tab: %d, over: %d", minusHeight, overHeight);
            } else if (((ConstraintLayout) getParent()).getChildAt(i) instanceof BottomBar) {
                minusHeight = ((ConstraintLayout) getParent()).getChildAt(i).getHeight();
                overHeight -= minusHeight;
//                Timber.i("calculateRest minus Bot: %d, over: %d", minusHeight, overHeight);
            } else if (mContext.getResources().getResourceEntryName(((ConstraintLayout) getParent()).getChildAt(i).getId()).equals(mContext.getResources().getString(R.string.cover_res_name))
                    && ((ConstraintLayout) getParent()).getChildAt(i).getVisibility() == View.VISIBLE) {
                minusHeight = ((ConstraintLayout) getParent()).getChildAt(i).getHeight();
                overHeight -= minusHeight;
//                Timber.i("calculateRest minus cover: %d, over: %d", minusHeight, overHeight);
            }
        }

        return overHeight;
    }

    public void setUpMode(final PostPresenter.Mode mode) {
        mMode = mode;
    }

    public void setSimpleGradient(final GradientDrawable simpleGradient) {
        Glide.clear(mImageViewTop);
        Glide.with(mContext)
                .load("")
                .asBitmap()
                .placeholder(simpleGradient)
                .into(mImageViewMid);
        Glide.clear(mImageViewBot);
    }

    public void setPng(final Integer pngResId) {
        Glide.clear(mImageViewTop);
        Glide.with(mContext)
                .load(pngResId)
                .asBitmap()
                .into(mImageViewMid);
        Glide.clear(mImageViewBot);
    }

    public void setAsset(final List<Integer> asset) {

        Glide.with(mContext)
                .load(asset.get(0))
                .asBitmap()
                .into(mImageViewTop);
        Glide.with(mContext)
                .load(asset.get(1))
                .asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(mImageViewMid);
        Glide.with(mContext)
                .load(asset.get(2))
                .asBitmap()
                .into(mImageViewBot);
    }

    public void setOnTextTouchListener(@NonNull final OnTouchListener onTextTouchListener) {
        mPostEditText.setOnTouchListener(onTextTouchListener);
    }

    public void setUpPhoto(final PhotoSquareBase photo) {
        Glide.clear(mImageViewTop);
        Glide.clear(mImageViewBot);
        Glide.with(mContext)
                .load((String) photo.getResource())
                .asBitmap()
                .fitCenter()
                .into(mImageViewMid);
    }

    public void setImageBitmap(@NonNull final Bitmap imageBitmap) {
        Glide.clear(mImageViewMid);
        mImageViewMid.setImageBitmap(imageBitmap);
    }

    public void setTextStyle(final TextStyle textStyle) {
        Timber.i("setTextStyle %d %d", textStyle.getBackgroundColour(), textStyle.getTextColour());
        if (textStyle.getBackgroundColour() == android.R.color.transparent) {
            mPostEditText.setTextColor(ContextCompat.getColor(getContext(), textStyle.getTextColour()));
        } else {

            String text = mPostEditText.getText().toString();

            Spannable span = new SpannableString(text);
            span.setSpan(
                    new SpanColoredBackground(
                            ContextCompat.getColor(mContext, textStyle.getBackgroundColour()),
                            (int) getResources().getDimension(R.dimen.square_corner),
                            (int) getResources().getDimension(R.dimen.square_corner)),
                    0,
                    text.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mPostEditText.setTextColor(ContextCompat.getColor(getContext(), textStyle.getTextColour()));
            mPostEditText.setText(span);
        }
        mPostEditText.setSelection(mPostEditText.length());
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        super.onLayout(changed, l, t, r, b);

//        if (mStickerView.getStickerCount() != 0) {
//            BitmapStickerIcon s = (BitmapStickerIcon) mStickerView.getCurrentSticker();
//            if (s == null)
//                return;
//            float[] f = new float[9];
//            s.getMatrix().getValues(f);
//            Matrix m = new Matrix();
//            f[Matrix.MTRANS_X] = 10;
//            f[Matrix.MTRANS_Y] = 10;
//            Timber.i("onLayout scale X :%f, Y :%f", f[Matrix.MSCALE_X], f[Matrix.MSCALE_Y]);
//            m.setValues(f);
//            s.setMatrix(m);
//        }
//        if (cachedSticker != null) {
//            float[] f = new float[9];
//            cachedSticker.getMatrix().getValues(f);
//            Matrix m = new Matrix();
//            f[Matrix.MTRANS_X] = 10;
//            f[Matrix.MTRANS_Y] = 10;
//            Timber.i("onLayout scale X :%f, Y :%f", f[Matrix.MSCALE_X], f[Matrix.MSCALE_Y]);
//            m.setValues(f);
//            cachedSticker.setMatrix(m);
//        }

        if (cachedSticker != null && cachedMatrix != null) {
            cachedSticker.setMatrix(cachedMatrix);
        }
        mStickerView.invalidate();
    }

    public void setSticker(final Sticker sticker) {
        try {
            BitmapStickerIcon icon = new BitmapStickerIcon(Drawable.createFromStream(mContext.getAssets().open(sticker.getPath()), null), LEFT_TOP);

            cachedSticker = icon;

            mStickerView.addSticker(icon);
            mStickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
                @Override
                public void onStickerAdded(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {

                }

                @Override
                public void onStickerClicked(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {

                }

                @Override
                public void onStickerDeleted(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {

                }

                @Override
                public void onStickerDragFinished(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {
                    Timber.i("onStickerDragFinished");
                    float[] values = new float[9];
                    Matrix clone = sticker.getMatrix();
                    clone.getValues(values);
                    cachedMatrix = new Matrix();
                    cachedMatrix.setValues(values);
                    Timber.i("onStickerDragFinished new m-x p-s: %f, %f", values[Matrix.MTRANS_X], values[Matrix.MTRANS_Y]);
                }

                @Override
                public void onStickerZoomFinished(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {

                }

                @Override
                public void onStickerFlipped(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {

                }

                @Override
                public void onStickerDoubleTapped(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

