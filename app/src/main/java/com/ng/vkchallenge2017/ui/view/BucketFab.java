package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.presentation.PostPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by NG on 20.09.17.
 */

public class BucketFab extends RelativeLayout {

    @BindView(R.id.post_bucket_image)
    ImageView mImageView;
    @BindView(R.id.bucket_corner_outline)
    View mViewOutline;
    @BindView(R.id.bucket_background)
    View mViewBackground;

    private LayoutInflater mInflater;
    private boolean isGrowed = false;
    private PostPresenter.Mode mMode;

    public boolean isGrowed() {
        return isGrowed;
    }

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
        if (getVisibility() != (isVisible ? VISIBLE : INVISIBLE)) {
            Timber.i("setVisibility, change! to %b", isVisible);
            setVisibility(isVisible ? VISIBLE : INVISIBLE);
        }

        if (isVisible) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getLayoutParams();
            params.setMargins(
                    0,
                    0,
                    0,
                    (mMode == PostPresenter.Mode.HISTORY ?
                            (int) getContext().getResources().getDimension(R.dimen.bottom_bar_height) : 0)
                            + (int) getContext().getResources().getDimension(R.dimen.bucket_bot_margin));
            requestLayout();
        }
    }

    public void grow(final boolean needGrow) {
        if (needGrow && !isGrowed) {
            Timber.i("grow CHANGE TO %b", needGrow);
            isGrowed = true;

            increase(true);
        } else if (!needGrow && isGrowed) {
            Timber.i("grow CHANGE TO %b", needGrow);
            isGrowed = false;
            increase(false);
        }
    }

    private void increase(final boolean needGrow) {
        Timber.i("increase CHANGE TO %b", needGrow);
        mViewOutline.setVisibility(needGrow ? INVISIBLE : VISIBLE);

        LayoutParams params = new LayoutParams(
                (int) getContext().getResources().getDimension(needGrow ? R.dimen.bucket_corner_enabled : R.dimen.bucket_corner_disabled),
                (int) getContext().getResources().getDimension(needGrow ? R.dimen.bucket_corner_enabled : R.dimen.bucket_corner_disabled));
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        mViewBackground.setLayoutParams(params);

        mImageView.setImageResource(needGrow ? R.drawable.ic_fab_trash_released : R.drawable.ic_fab_trash);
    }

    public void setMode(final PostPresenter.Mode mode) {
        mMode = mode;
    }
}
