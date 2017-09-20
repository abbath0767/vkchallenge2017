package com.ng.vkchallenge2017.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ng.vkchallenge2017.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 20.09.17.
 */

public class ProgressDialog extends Dialog {

    @BindView(R.id.loading_text_view)
    TextView mTextView;
    @BindView(R.id.loading_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.loading_button)
    Button mButton;
    @BindView(R.id.loading_image_icon)
    ImageView mImageView;
    @BindView(R.id.loading_image_corner)
    View mViewCorner;

    public ProgressDialog(@NonNull final Context context) {
        super(context);
    }

    public ProgressDialog(@NonNull final Context context, final int themeResId) {
        super(context, themeResId);
    }

    public ProgressDialog(@NonNull final Context context, final boolean cancelable, @Nullable final OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    public void show() {
        super.show();
        mTextView.setText(R.string.publishing);
        mButton.setText(R.string.cancel);
        mButton.setBackgroundResource(R.drawable.loading_button_load);
        mProgressBar.setVisibility(View.VISIBLE);
        Timber.i("show. %d", mProgressBar.getHeight());
    }

    public void finishLoading() {
        mProgressBar.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
        mViewCorner.setVisibility(View.VISIBLE);
        mButton.setText(R.string.create_more);
        mTextView.setText(R.string.publishing_complite);
        mButton.setTextColor(getContext().getResources().getColor(android.R.color.white));
        mButton.setBackgroundResource(R.drawable.loading_button_finish);
    }

    public void setOnClickListener(@NonNull final  View.OnClickListener listener) {
        mButton.setOnClickListener(listener);
    }
}
