package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.presentation.PostPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 11.09.17.
 */

public class CustomImageView extends ConstraintLayout {

    @BindView(R.id.image_view_top)
    ImageView mImageViewTop;
    @BindView(R.id.image_view_mid)
    ImageView mImageViewMid;
    @BindView(R.id.image_view_bot)
    ImageView mImageViewBot;

    private LayoutInflater mInflater;
    private PostPresenter.Mode mMode = PostPresenter.Mode.POST;

    public CustomImageView(final Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public CustomImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public CustomImageView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        mInflater.inflate(R.layout.image_collection, this, true);
        ButterKnife.bind(this);
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
    //todo 328 pixel MF
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        Timber.i("onMeasure. mode: %s", mMode);

        int overHeight = calculateRest();
        int overWidth = calculateWidth();
        Timber.i("onMeasure. rest H: %d, rest W: %d, measure H: %d, measure W: %d", overHeight, overWidth, heightMeasureSpec, widthMeasureSpec);

        if (mMode == PostPresenter.Mode.POST) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            ViewGroup.LayoutParams p = getLayoutParams();
            if (overWidth >= overHeight) {
                Timber.i("onMeasure. setDimen: %d x %d", overWidth, overHeight);
                setMeasuredDimension(overWidth, overHeight);
                p.width = overWidth;
                p.height = overHeight;
                setLayoutParams(p);
            } else {
                Timber.i("onMeasure, setDimen: %d x %d", getMeasuredWidth(), getMeasuredHeight());
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
                p.width = overWidth;
                p.height = overHeight;
                setLayoutParams(p);
            }
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
}
