package com.ng.vkchallenge2017.ui.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.Util.GradientDrawableFactory;
import com.ng.vkchallenge2017.presentation.MainPresenter;
import com.ng.vkchallenge2017.view.MainViewContract;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainViewContract.View {

    MainViewContract.Presenter mMainPresenter;

    @BindView(R.id.main_activity_layout)
    ConstraintLayout mConstraintLayoutMain;
    @BindView(R.id.main_activity_login_button)
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initPresenter();

        setUpGradient();
    }

    private void initPresenter() {
        mMainPresenter = new MainPresenter(this);
    }

    private void setUpGradient() {
        mConstraintLayoutMain.setBackgroundDrawable(
                GradientDrawableFactory.getGradient(
                        R.color.cornflower_blue, R.color.cool_blue,
                        GradientDrawable.Orientation.TOP_BOTTOM, this));
    }

    @OnClick(R.id.main_activity_login_button)
    public void onLoginClick() {
        mMainPresenter.onLoginClick();
    }

    @Override
    public void makeLoginCall() {
        VKSdk.login(this, VKScope.WALL, VKScope.PHOTOS);
    }

    //todo ADD ERORR HANDLING!
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.i("onActivityResult. requestCode: %d\nresultCode: %d", requestCode, resultCode);

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Timber.i("onResult. Success login! res: %s", res.toString());
                mMainPresenter.loginSuccess();
            }

            @Override
            public void onError(VKError error) {
                Timber.i("onError. error login! error: %s", error.toString());
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showPostActivity() {
        Timber.i("showPostActivity");
        startActivity(
                new Intent(this, PostActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
