package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.presentation.PostPresenter;

import java.util.List;

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
        int rest = calculateRest();
//        int overWidth = getMeasuredWidth();
//        Timber.i("onMeasure specs W: %d, H: %d", widthMeasureSpec, heightMeasureSpec);
//        Timber.i("onMeasure get() W: %d, H: %d", getMeasuredWidth(), getMeasuredHeight());
//        Timber.i("onMeasure getSpec() W: %d, H: %d", MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        int exceptedHeight = MeasureSpec.getSize(widthMeasureSpec);
        int realHeight = MeasureSpec.getSize(heightMeasureSpec);

//        Timber.i("onMeasure. excepted: %d, real: %d", exceptedHeight, realHeight);
        if (mMode == PostPresenter.Mode.POST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            if (realHeight > exceptedHeight) {
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
//                Timber.i("calculateRest minus Tab: %d", overHeight);
            } else if (((ConstraintLayout) getParent()).getChildAt(i) instanceof BottomBar) {
                minusHeight = ((ConstraintLayout) getParent()).getChildAt(i).getHeight();
                overHeight -= minusHeight;
//                Timber.i("calculateRest minus Bot: %d", overHeight);
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
//        mImageViewMid.setImageResource(pngResId);
        mImageViewMid.setImageBitmap(decodeSampledBitmapFromResource(mContext.getResources(), pngResId, getWidth(), getHeight()));
    }

    public void setAsset(final List<Integer> asset) {
        mImageViewTop.setImageResource(asset.get(0));
        mImageViewMid.setImageResource(asset.get(1));
        mImageViewBot.setImageResource(asset.get(2));

        Timber.i("setAsset");
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    //todo make async!
    private int calculateTextColour() {

        Bitmap midBitMap = null;
        try {
            Timber.i("calculateTextColour. %s", mImageViewMid.getDrawable());
            if (mImageViewMid.getDrawable() instanceof BitmapDrawable) {
                midBitMap = ((BitmapDrawable) mImageViewMid.getDrawable()).getBitmap();
            } else {
                Timber.i("calculateTextColour. %d", getHeight());
                if (getWidth() == 0)
                    return 0;
                midBitMap = Bitmap.createBitmap(getWidth()/10, getHeight()/10, Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(midBitMap);
                mImageViewMid.draw(canvas);
            }

            Palette p = new Palette.Builder(midBitMap).generate();

            Palette.Swatch swatch = p.getVibrantSwatch();

            if (swatch == null)
                return 0;
            else
                return swatch.getTitleTextColor();
        } finally {
            midBitMap = null;
        }
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
