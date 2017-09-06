package com.ng.vkchallenge2017.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.presentation.PostPresenter;
import com.ng.vkchallenge2017.view.PostView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class PostActivity extends MvpAppCompatActivity implements PostView {

    @InjectPresenter
    PostPresenter mPostPresenter;

    @BindView(R.id.post_tab_layout)
    TabLayout mTabLayout;
    //todo add border ! (140 character!)
    @BindView(R.id.post_edit_text)
    EditText mPostEditText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        moveStripToTop();
        //todo
        disableClickAnimation();
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
}
