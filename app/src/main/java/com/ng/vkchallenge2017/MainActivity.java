package com.ng.vkchallenge2017;

import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ng.vkchallenge2017.Util.GradientDrawableFactory;
import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_activity_layout)
    ConstraintLayout mConstraintLayoutMain;
    @BindView(R.id.main_activity_login_button)
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mConstraintLayoutMain.setBackgroundDrawable(
                GradientDrawableFactory.getGradient(
                        R.color.cornflower_blue, R.color.cool_blue,
                        GradientDrawable.Orientation.TOP_BOTTOM, this));
    }
}
