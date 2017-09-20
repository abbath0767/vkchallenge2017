package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ng.vkchallenge2017.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NG on 20.09.17.
 */

public class BucketFab extends ConstraintLayout {

    @BindView(R.id.post_bucket_image)
    ImageView mImageView;
    @BindView(R.id.bucket_corner_outline)
    View mViewOutline;
    @BindView(R.id.bucket_background)
    View mViewBackground;


    private LayoutInflater mInflater;

    public BucketFab(final Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public BucketFab(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    public BucketFab(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        mInflater.inflate(R.layout.bucket_view, this, true);
        ButterKnife.bind(this);

    }

    public void setVisibility(@NonNull final boolean isVisible) {
        setVisibility(isVisible ? VISIBLE : INVISIBLE);
    }
}
