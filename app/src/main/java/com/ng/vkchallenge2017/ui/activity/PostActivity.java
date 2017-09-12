package com.ng.vkchallenge2017.ui.activity;

import android.app.Service;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.Util.GradientDrawableFactory;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.GradientColours;
import com.ng.vkchallenge2017.model.square.SimpleThumb;
import com.ng.vkchallenge2017.model.square.Thumb;
import com.ng.vkchallenge2017.model.square.ThumbAsset;
import com.ng.vkchallenge2017.model.square.Thumbs;
import com.ng.vkchallenge2017.presentation.PostPresenter;
import com.ng.vkchallenge2017.ui.view.BottomBar;
import com.ng.vkchallenge2017.ui.view.BottomSquareRVAdapter;
import com.ng.vkchallenge2017.ui.view.CustomImageView;
import com.ng.vkchallenge2017.ui.view.KeyBoardListener;
import com.ng.vkchallenge2017.view.PostView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class PostActivity extends MvpAppCompatActivity implements PostView {

    @InjectPresenter
    PostPresenter mPostPresenter;

    @BindView(R.id.post_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.post_image_view)
    CustomImageView mCustomImageView;
    @BindView(R.id.post_bottom_bar)
    BottomBar mBottomBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        moveStripToTop();
        //todo
        disableClickAnimation();

        setUpKeyListener();

        mBottomBar.setSquareClickListener(new BottomSquareRVAdapter.SquareClickListener() {
            @Override
            public void onSquareClick(final int position) {
                mPostPresenter.onSquareClick(position);
            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                mPostPresenter.tabSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(final TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(final TabLayout.Tab tab) {
            }
        });
    }

    private void setUpKeyListener() {
        KeyBoardListener.observeKeyBoard(this, new KeyBoardListener.KeyBoardMoveListener() {
            @Override
            public void isShown(final boolean isShowing) {
                requestImageView();
            }
        });
    }

    private void moveStripToTop() {
        int tabCount = ((LinearLayout)mTabLayout.getChildAt(0)).getChildCount();
        for (int i = 0; i < tabCount; i++) {
            //get reverted tab and revert only textview
            ((((LinearLayout)((LinearLayout)mTabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1))).setScaleY(-1);
        }
    }

    private void disableClickAnimation() {
    }

    @Override
    public void setBottomBarRecycler(final List<BottomSquareBase> squares) {
        mBottomBar.setSquares(squares);
    }

    @Override
    public void setUpContent(final BottomSquareBase square) {
        Timber.i("setUpContent. square: %s", square);
        clearImageView();
        switch (square.getType()) {
            case DEFAULT: {
//                mCustomImageView.clear();
                //clear view
                break;
            }
            case COLOR: {
                mCustomImageView.setSimpleGradient(GradientDrawableFactory.getGradient(
                        ((GradientColours)square.getResource()).getStartColour(),
                        ((GradientColours)square.getResource()).getEndColour(),
                        GradientDrawable.Orientation.TL_BR,
                        this
                ));
                break;
            }
            case THUMB: {
                Thumb thumb = ((Thumbs)square.getResource()).getBigThumb();
                if (thumb instanceof SimpleThumb) {
                    mCustomImageView.setPng(((SimpleThumb) thumb).getResource());
                } else if (thumb instanceof ThumbAsset) {
//                    mCustomImageView.setAsset((ThumbAsset)thumb).getResource();
                }

                break;
            }
        }

        requestImageView();
    }

    private void requestImageView() {
        mCustomImageView.requestLayout();
        mCustomImageView.calculateAverageColour();
    }

    private void clearImageView() {
        mCustomImageView.clear();
    }

    @Override
    public void setUpMode(final PostPresenter.Mode mode) {
        mCustomImageView.setUpMode(mode);
    }
}
