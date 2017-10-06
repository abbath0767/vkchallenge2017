package com.ng.vkchallenge2017.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;
import com.ng.vkchallenge2017.model.sticker.Sticker;
import com.ng.vkchallenge2017.model.text_style.TextStyle;
import com.ng.vkchallenge2017.presentation.PostPresenter;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.StickerView;

import java.io.IOException;
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
    @BindView(R.id.bucket_fab)
    BucketFab mBucketFab;
    @BindView(R.id.image_parent)
    ConstraintLayout mConstraintLayoutParent;

    private Context mContext;

    private LayoutInflater mInflater;
    private PostPresenter.Mode mMode = PostPresenter.Mode.POST;

    private Map<BitmapStickerIcon, Matrix> cash = new HashMap<>();
    private int oldHeight = 0;

    private boolean mBooleanIsPressed = false;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            showBucket(true);
        }
    };
    private Rect mRectBucket;

    private BackgroundEditText mBackgroundEditText;

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

        bringToFrontViews();
        mBucketFab.setVisibility(false);
        setStickerListener();
    }

    private void setStickerListener() {
        mStickerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                Timber.i("onTouch %s", mStickerView.getCurrentSticker());

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Timber.i("onTouch ACTION DOWN");
                    if (mBucketFab.getVisibility() == View.INVISIBLE) {
                        Timber.i("onTouch SHOW");
                        handler.postDelayed(runnable, 2000);
                    }

                    mBooleanIsPressed = true;
                }

                if (mRectBucket.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    Timber.i("onTouch GROW");
                    growButton(true);
                } else {
                    Timber.i("onTouch UN GROW");
                    growButton(false);
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Timber.i("onTouch ACTION UP");
                    if (mBooleanIsPressed) {
                        mBooleanIsPressed = false;
                        handler.removeCallbacks(runnable);
                        showBucket(false);

                        if (mRectBucket.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                            Timber.i("onTouch NEED DELETE");
                            mStickerView.remove(mStickerView.getCurrentSticker());
                        }
                    }
                }

                return false;
            }
        });
    }

    private void bringToFrontViews() {
        mStickerView.bringToFront();
        mPostEditText.bringToFront();
        mPostEditText.requestLayout();
        mBucketFab.bringToFront();
        mBucketFab.requestLayout();
        mRectBucket = new Rect();
    }

    private void showBucket(boolean isVisible) {
        if (mStickerView.getCurrentSticker() != null)
            mBucketFab.setVisibility(isVisible);
    }

    public void setOnTextChangeListener(@NonNull final TextWatcher textWatcher) {
        mPostEditText.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        int rest = calculateRest();
//        int overWidth = getMeasuredWidth();
//        Timber.i("onMeasure specs W: %d, H: %d", widthMeasureSpec, heightMeasureSpec);
//        Timber.i("onMeasure get() W: %d, H: %d", getMeasuredWidth(), getMeasuredHeight());
//        Timber.i("onMeasure getSpec() W: %d, H: %d", MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        int exceptedHeight = MeasureSpec.getSize(widthMeasureSpec);
//        int realHeight = MeasureSpec.getSize(heightMeasureSpec);

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
            if (rest < exceptedHeight) {
//                int spec = MeasureSpec.makeMeasureSpec(rest, MeasureSpec.EXACTLY);
                int spec = MeasureSpec.makeMeasureSpec(rest, MeasureSpec.AT_MOST);
                setMeasuredDimension(exceptedHeight, rest);
                super.onMeasure(widthMeasureSpec, spec);
            }
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
        mBucketFab.setMode(mode);
        requestLayout();
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
        mPostEditText.setTextColor(ContextCompat.getColor(getContext(), textStyle.getTextColour()));

        addBackgroundView(ContextCompat.getColor(getContext(), textStyle.getBackgroundColour()));

        mPostEditText.setSelection(mPostEditText.length());
    }

    @SuppressLint("NewApi")
    private void addBackgroundView(@NonNull final int color) {
        if (mBackgroundEditText != null || mPostEditText.length() == 0) {
            mConstraintLayoutParent.removeView(mBackgroundEditText);
            mBackgroundEditText = null;
        }

        if (color != android.R.color.transparent) {
            mBackgroundEditText = new BackgroundEditText.Builder()
                    .context(getContext())
                    .forText(mPostEditText)
                    .withColor(color)
                    .withRadius(((int) getResources().getDimension(R.dimen.square_corner)))
                    .withHorizontalPadding(((int) getResources().getDimension(R.dimen.text_corner)))
                    .build();

            mBackgroundEditText.setId(View.generateViewId());
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(0, 0);
            mBackgroundEditText.setLayoutParams(params);

            ConstraintSet set = new ConstraintSet();
            mConstraintLayoutParent.addView(mBackgroundEditText, 0);
            set.clone(mConstraintLayoutParent);
            set.connect(mBackgroundEditText.getId(), ConstraintSet.TOP, mPostEditText.getId(), ConstraintSet.TOP);
            set.connect(mBackgroundEditText.getId(), ConstraintSet.BOTTOM, mPostEditText.getId(), ConstraintSet.BOTTOM);
            set.connect(mBackgroundEditText.getId(), ConstraintSet.LEFT, mPostEditText.getId(), ConstraintSet.LEFT);
            set.connect(mBackgroundEditText.getId(), ConstraintSet.RIGHT, mPostEditText.getId(), ConstraintSet.RIGHT);
            set.applyTo(mConstraintLayoutParent);

            mBackgroundEditText.bringToFront();
            mPostEditText.bringToFront();
        }
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        super.onLayout(changed, l, t, r, b);

        if (mBucketFab.getVisibility() != View.VISIBLE) {
            int newHeight = mStickerView.getHeight();
            Timber.i("onLayout, size: %d, new : %d, old: %d", cash.size(), newHeight, oldHeight);
            for (Map.Entry<BitmapStickerIcon, Matrix> entry : cash.entrySet()) {
                if (oldHeight != 0) {
                    entry.getKey().setMatrix(entry.getValue());

                    float[] f = new float[9];
                    entry.getValue().getValues(f);
                    entry.getKey().getMatrix().getValues(f);

                    float ratio = (float) newHeight / (float) oldHeight;
                    f[Matrix.MTRANS_Y] = f[Matrix.MTRANS_Y] * ratio;
                    entry.getValue().setValues(f);
                    entry.getKey().setMatrix(entry.getValue());

                    entry.getKey().getMatrix().getValues(f);
                }
            }

            mStickerView.invalidate();
            oldHeight = newHeight;
        }

        mBucketFab.getHitRect(mRectBucket);
        mBackgroundEditText.init();
    }

    private void growButton(boolean needGrow) {
        mBucketFab.grow(needGrow);
    }

    public void setSticker(final Sticker sticker) {
        try {
            BitmapStickerIcon icon = new BitmapStickerIcon(Drawable.createFromStream(mContext.getAssets().open(sticker.getPath()), null), LEFT_TOP);

            mStickerView.addSticker(icon);

            mStickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
                @Override
                public void onStickerAdded(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {
//                    Timber.i("onStickerAdded");
                    cash.put((BitmapStickerIcon) sticker, sticker.getMatrix());
                }

                @Override
                public void onStickerClicked(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {
//                    Timber.i("onStickerClicked");
                }

                @Override
                public void onStickerDeleted(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {
//                    Timber.i("onStickerDeleted");
                    cash.remove((BitmapStickerIcon) sticker);
                }

                @Override
                public void onStickerDragFinished(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {
//                    Timber.i("onStickerDragFinished");
                    float[] values = new float[9];
                    Matrix old = sticker.getMatrix();
                    old.getValues(values);
                    Matrix cached = new Matrix();
                    cached.setValues(values);
//                    Timber.i("onStickerDragFinished X %f Y %f", values[Matrix.MTRANS_X], values[Matrix.MTRANS_Y]);
                    cash.put((BitmapStickerIcon) sticker, cached);
                }

                @Override
                public void onStickerZoomFinished(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {
//                    Timber.i("onStickerZoomFinished");
                    float[] values = new float[9];
                    Matrix old = sticker.getMatrix();
                    old.getValues(values);
                    Matrix cached = new Matrix();
                    cached.setValues(values);
                    cash.put((BitmapStickerIcon) sticker, cached);
                }

                @Override
                public void onStickerFlipped(@NonNull final com.xiaopo.flying.sticker.Sticker sticker) {
                    Timber.i("onStickerFlipped");
                    float[] values = new float[9];
                    Matrix old = sticker.getMatrix();
                    old.getValues(values);
                    Matrix cached = new Matrix();
                    cached.setValues(values);
                    cash.put((BitmapStickerIcon) sticker, cached);
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

