package com.ng.vkchallenge2017.ui.activity;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.view.PostView;

import butterknife.ButterKnife;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class PostActivity extends MvpAppCompatActivity implements PostView {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

    }
}
