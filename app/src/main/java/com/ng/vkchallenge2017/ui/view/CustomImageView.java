package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.presentation.PostPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 11.09.17.
 */

public class CustomImageView extends ConstraintLayout {

    @BindView(R.id.post_edit_text)
    EditText mPostEditText;
    @BindView(R.id.image_view_top)
    ImageView mImageViewTop;
    @BindView(R.id.image_view_mid)
    ImageView mImageViewMid;
    @BindView(R.id.image_view_bot)
    ImageView mImageViewBot;

    private Context mContext;

    private LayoutInflater mInflater;
    private PostPresenter.Mode mMode = PostPresenter.Mode.POST;

    private ColorStateList oldColors;


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

        saveDefaultTextColour();
    }

    public void clear() {
        mImageViewTop.setImageResource(android.R.color.transparent);
        mImageViewTop.setBackgroundResource(android.R.color.transparent);
        mImageViewMid.setImageResource(android.R.color.transparent);
        mImageViewMid.setBackgroundResource(android.R.color.transparent);
        mImageViewBot.setImageResource(android.R.color.transparent);
        mImageViewBot.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        int overHeight = calculateRest();
        int overWidth = calculateWidth();

        if (mMode == PostPresenter.Mode.POST) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            Timber.i("onMeasure H %d, W %d", overHeight, overWidth);

            if (overWidth > overHeight) {
                Timber.i("onMeasure NOT");
                setMeasuredDimension(overWidth, overHeight);
            } else if (overWidth <= overHeight) {
                Timber.i("onMeasure SQUARE");
                setMeasuredDimension(overWidth, overWidth);
            }

//            if (overWidth >= overHeight) {
////                Timber.i("onMeasure. setDimen: %d x %d", overWidth, overHeight);
//                setMeasuredDimension(overWidth, overHeight);
//            } else {
////                Timber.i("onMeasure, setDimen: %d x %d", getMeasuredWidth(), getMeasuredHeight());
//                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
//            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private int calculateWidth() {
        return ((ConstraintLayout) getParent()).getWidth();
    }

    private int calculateRest() {
        int overHeight = 0;
        overHeight += ((ConstraintLayout) getParent()).getHeight();
        for (int i = 0; i < ((ConstraintLayout) getParent()).getChildCount(); i++) {
            int minusHeight = 0;
            if (((ConstraintLayout) getParent()).getChildAt(i) instanceof TabLayout) {
                minusHeight = (((ConstraintLayout) getParent()).getChildAt(i)).getHeight();
                overHeight -= minusHeight;
            } else if (((ConstraintLayout) getParent()).getChildAt(i) instanceof BottomBar) {
                minusHeight = ((ConstraintLayout) getParent()).getChildAt(i).getHeight();
                overHeight -= minusHeight;
            }
        }

        return overHeight;
    }

    public void setUpMode(final PostPresenter.Mode mode) {
        mMode = mode;
    }

    public void setSimpleGradient(final GradientDrawable simpleGradient) {
        mImageViewMid.setBackground(simpleGradient);
    }

    public void setPng(final Integer pngResId) {
        mImageViewMid.setImageResource(pngResId);
    }

    //todo make async!
    private int calculateTextColour() {

        Bitmap midBitMap;
        Timber.i("calculateTextColour. %s", mImageViewMid.getDrawable());
        if (mImageViewMid.getDrawable() instanceof BitmapDrawable) {
            midBitMap = ((BitmapDrawable) mImageViewMid.getDrawable()).getBitmap();
        } else {
            midBitMap = Bitmap.createBitmap(mImageViewMid.getWidth(), mImageViewMid.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(midBitMap);
            mImageViewMid.draw(canvas);
        }

        Palette p = new Palette.Builder(midBitMap).generate();

        Palette.Swatch swatch = p.getVibrantSwatch();

        if (swatch == null)
            return 0;
        else
            return swatch.getTitleTextColor();
    }

    public void calculateAverageColour() {
        int color = calculateTextColour();
        if (color == 0) {
            mPostEditText.setTextColor(oldColors);
        } else {
            mPostEditText.setTextColor(color);
        }
    }

    private void saveDefaultTextColour() {
        oldColors = mPostEditText.getTextColors();
    }
}

