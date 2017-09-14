package com.ng.vkchallenge2017.ui.activity;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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

import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class PostActivity extends MvpAppCompatActivity implements PostView {

    @InjectPresenter
    PostPresenter mPostPresenter;

    @BindView(R.id.post_container)
    ConstraintLayout mParentLayout;
    @BindView(R.id.post_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.post_image_view)
    CustomImageView mCustomImageView;
    @BindView(R.id.post_bottom_bar)
    BottomBar mBottomBar;

    @BindView(R.id.cover_for_popup)
    ConstraintLayout cover;

    private InputMethodManager mInputMethodManager;

    View popupView;
    PopupWindow mPopupWindow;
    private int keyboardHeight;
    private boolean isKeyBoardVisible;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        initInputManager();

        moveStripToTop();
        //todo
        disableClickAnimation();

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

        inflatePopup();
        enablePopup();

        setUpKeyListener();

        mCustomImageView.setOnTextTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                disablePopup();
                return false;
            }
        });
    }

    private void initInputManager() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private int oldValue = 0;

    private void setUpKeyListener() {
        KeyBoardListener.observeKeyBoard(mParentLayout, mPopupWindow, new KeyBoardListener.KeyBoardMoveListener() {
            @Override
            public void isShown(final boolean isShowing, final int heightDifference) {
                Timber.i("isShown %b, oldValue: %d, keyHeightt: %d", isShowing, heightDifference, keyboardHeight);

                isKeyBoardVisible = isShowing;

                if (heightDifference != oldValue) {
                    Timber.i("isShown %b new %d old %d", isShowing, heightDifference, oldValue);
                    oldValue = heightDifference;
                    changeKeyboardHeight(heightDifference);
                    requestImageView();
                }
            }
        });
    }

    private void moveStripToTop() {
        int tabCount = ((LinearLayout) mTabLayout.getChildAt(0)).getChildCount();
        for (int i = 0; i < tabCount; i++) {
            //get reverted tab and revert only textview
            ((((LinearLayout) ((LinearLayout) mTabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1))).setScaleY(-1);
        }
    }

    private void disableClickAnimation() {
    }

    @Override
    public void setBottomBarRecycler(final List<BottomSquareBase> squares) {
        mBottomBar.setSquares(squares);
    }

    private void forceShowKeyboardIfNeed() {
        if (!isKeyBoardVisible) {
            isKeyBoardVisible = true;
            mInputMethodManager.toggleSoftInputFromWindow(
                    mParentLayout.getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);
        }
    }

    @Override
    public void setUpContent(final BottomSquareBase square) {
        switch (square.getType()) {
            case DEFAULT: {
                mCustomImageView.setPng((int) square.getResource());
                break;
            }
            case COLOR: {
                mCustomImageView.setSimpleGradient(GradientDrawableFactory.getGradient(
                        ((GradientColours) square.getResource()).getStartColour(),
                        ((GradientColours) square.getResource()).getEndColour(),
                        GradientDrawable.Orientation.TL_BR,
                        this
                ));
                break;
            }
            case THUMB: {
                Thumb thumb = ((Thumbs) square.getResource()).getBigThumb();
                if (thumb instanceof SimpleThumb) {
                    mCustomImageView.setPng(((SimpleThumb) thumb).getResource());
                } else if (thumb instanceof ThumbAsset) {
                    mCustomImageView.setAsset(((ThumbAsset) thumb).getResource());
                }

                break;
            }
        }

        requestImageView();
    }

    @Override
    public void disablePopup() {
        if (mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    @Override
    public void setUpPopup() {
        forceShowKeyboardIfNeed();
        Timber.i("setUpPopup. NEED SHOW POP UP, keyboard: %b, H: %d", isKeyBoardVisible, keyboardHeight);

        if (!mPopupWindow.isShowing()) {

            mPopupWindow.setHeight((keyboardHeight));

            if (isKeyBoardVisible) {
                cover.setVisibility(LinearLayout.GONE);
            } else {
                cover.setVisibility(LinearLayout.VISIBLE);
            }
            mPopupWindow.showAtLocation(mParentLayout, Gravity.BOTTOM, 0, 0);

        } else {
            mPopupWindow.dismiss();
        }
    }

    private void requestImageView() {
        mCustomImageView.requestLayout();
        mCustomImageView.setCorrectTextColour();
    }

    @Override
    public void setUpMode(final PostPresenter.Mode mode) {
        mCustomImageView.setUpMode(mode);
    }

    private void inflatePopup() {
        Timber.i("inflatePopup");
        popupView = getLayoutInflater().inflate(R.layout.popup_photos, null);

        final float popUpheight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpheight);
    }

    private void enablePopup() {
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT,
                (int) keyboardHeight, false);


        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Timber.i("onDismiss.");
                cover.setVisibility(LinearLayout.GONE);
            }
        });
    }

    private void changeKeyboardHeight(final int height) {
        Timber.i("changeKeyboardHeight. %d", height);
        if (height > 100) {
            keyboardHeight = height;
            ConstraintSet set = new ConstraintSet();
            set.clone(cover);
            set.constrainWidth(cover.getId(), mParentLayout.getWidth());
            set.constrainHeight(cover.getId(), keyboardHeight);
//            set.connect(cover.getId(), ConstraintSet.BOTTOM, mParentLayout.getId(), ConstraintSet.BOTTOM);
//            set.connect(cover.getId(), ConstraintSet.LEFT, mParentLayout.getId(), ConstraintSet.LEFT);
//            set.connect(cover.getId(), ConstraintSet.RIGHT, mParentLayout.getId(), ConstraintSet.RIGHT);
            set.applyTo(cover);
        }
    }
}
