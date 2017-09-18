package com.ng.vkchallenge2017.ui.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.Util.GradientDrawableFactory;
import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.GradientColours;
import com.ng.vkchallenge2017.model.square.SimpleThumb;
import com.ng.vkchallenge2017.model.square.Thumb;
import com.ng.vkchallenge2017.model.square.ThumbAsset;
import com.ng.vkchallenge2017.model.square.Thumbs;
import com.ng.vkchallenge2017.model.sticker.Sticker;
import com.ng.vkchallenge2017.model.text_style.TextStyle;
import com.ng.vkchallenge2017.presentation.PostPresenter;
import com.ng.vkchallenge2017.repo.PhotoRepositoryImpl;
import com.ng.vkchallenge2017.repo.StickerRepositoryImpl;
import com.ng.vkchallenge2017.ui.adapter.PopupSquareAdapter;
import com.ng.vkchallenge2017.ui.adapter.StickerRVAdapter;
import com.ng.vkchallenge2017.ui.view.BottomBar;
import com.ng.vkchallenge2017.ui.adapter.BottomSquareRVAdapter;
import com.ng.vkchallenge2017.ui.view.CustomImageView;
import com.ng.vkchallenge2017.ui.view.KeyBoardListener;
import com.ng.vkchallenge2017.ui.view.StickerDialog;
import com.ng.vkchallenge2017.view.PostView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.StickerView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.xiaopo.flying.sticker.BitmapStickerIcon.LEFT_TOP;


/**
 * Created by nikitagusarov on 06.09.17.
 */

public class PostActivity extends MvpAppCompatActivity implements PostView {

    public static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 13;
    public static final int REQUEST_CAMERA = 23;
    public static final int REQUEST_GALLERY = 33;


    @InjectPresenter
    PostPresenter mPostPresenter;

    @ProvidePresenter
    public PostPresenter providePresenter() {
        return new PostPresenter(new PhotoRepositoryImpl(this), StickerRepositoryImpl.newInstance(this));
    }

    @BindView(R.id.post_toolbar_left_button)
    ImageButton mImageButtonLeft;
    @BindView(R.id.post_toolbar_right_button)
    ImageButton mImageButtonRight;
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
    @BindView(R.id.top_background)
    View mCoverBackground;


    private InputMethodManager mInputMethodManager;
    private View popupView;
    private PopupWindow mPopupWindow;
    private int keyboardHeight;
    private boolean isKeyBoardVisible;
//    private boolean keyboardNeedVisible = false;

    private RecyclerView mPhotoRecyclerView;
    private PopupSquareAdapter mPopupSquareAdapter;

    private Uri photoUri;

    private StickerDialog mStickerDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        moveStripToTop();
        disableClickAnimation();

        bringToFrontViews();

        mImageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPostPresenter.onButtonLeftClick();
            }
        });

        mImageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPostPresenter.onButtonRightClick();
            }
        });

        mBottomBar.setSquareClickListener(new BottomSquareRVAdapter.SquareClickListener() {
            @Override
            public void onSquareClick(final int position) {
                mPostPresenter.onSquareClick(position);
            }
        });

        mBottomBar.setSentButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                hideKeyboardIfNeed();
                mPostPresenter.sendClick();
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

        mCustomImageView.setOnTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
                mPostPresenter.textChange(editable.toString());
            }
        });

        initInputManager();
    }

    private void bringToFrontViews() {
        mCoverBackground.bringToFront();
        mCoverBackground.requestLayout();
        mImageButtonLeft.bringToFront();
        mImageButtonLeft.requestLayout();
        mImageButtonRight.bringToFront();
        mImageButtonRight.requestLayout();
        mTabLayout.bringToFront();
        mTabLayout.requestLayout();
        mBottomBar.bringToFront();
        mBottomBar.requestLayout();
    }

    private void initInputManager() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private int oldValue = 0;

    private void setUpKeyListener() {
        KeyBoardListener.observeKeyBoard(mParentLayout, mPopupWindow, new KeyBoardListener.KeyBoardMoveListener() {
            @Override
            public void isShown(final boolean isShowing, final int heightDifference) {

                isKeyBoardVisible = isShowing;

                if (heightDifference != oldValue) {
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
            ((((LinearLayout) ((LinearLayout) mTabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1))).setScaleY(-1);
            ((AppCompatTextView) ((((LinearLayout) ((LinearLayout) mTabLayout.getChildAt(0)).getChildAt(i)).getChildAt(1)))).setTextSize(14f);
        }

        mTabLayout.requestLayout();
    }

    private void disableClickAnimation() {
    }

    @Override
    public void setBottomBarRecycler(final List<BottomSquareBase> squares) {
        mBottomBar.setSquares(squares);
    }

    private void forceShowKeyboardIfNeed() {
        Timber.i("forceShowKeyboardIfNeed : %b", isKeyBoardVisible);
        if (!isKeyBoardVisible) {
            isKeyBoardVisible = true;
            mInputMethodManager.toggleSoftInputFromWindow(
                    mParentLayout.getApplicationWindowToken(),
                    InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private void hideKeyboardIfNeed() {
        Timber.i("hideKeyboardIfNeed : %b", isKeyBoardVisible);
        if (isKeyBoardVisible) {
            isKeyBoardVisible = false;
            mInputMethodManager.hideSoftInputFromWindow(
                    mParentLayout.getApplicationWindowToken(),
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
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
            case ACTION:
                return;
        }

        requestImageView();
    }

    @Override
    public void setUpPhoto(final PhotoSquareBase photo) {
        Timber.i("setUpPhoto");
        mCustomImageView.setUpPhoto(photo);
    }

    @Override
    public void setSelectedPhotoPosition(final int photoPosition) {
        Timber.i("setSelectedPhotoPosition: %d", photoPosition);
        mPopupSquareAdapter.setSelectedItem(photoPosition);
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
        if (!mPopupWindow.isShowing()) {

            mPopupWindow.setHeight((keyboardHeight));

            if (isKeyBoardVisible) {
                ConstraintSet set = new ConstraintSet();
                set.clone(mParentLayout);
                set.setVisibility(cover.getId(), ConstraintSet.GONE);
                set.applyTo(mParentLayout);
            } else {
                ConstraintSet set = new ConstraintSet();
                set.clone(mParentLayout);
                set.setVisibility(cover.getId(), ConstraintSet.VISIBLE);
                set.applyTo(mParentLayout);
            }
            cover.invalidate();
            mParentLayout.requestLayout();

            mPopupWindow.showAtLocation(mParentLayout, Gravity.BOTTOM, 0, 0);

        } else {
            mPopupWindow.dismiss();
        }
    }

    private void requestImageView() {
        mCustomImageView.requestLayout();
    }

    @Override
    public void setUpMode(final PostPresenter.Mode mode) {
        mCustomImageView.setUpMode(mode);
    }

    private void inflatePopup() {
        popupView = getLayoutInflater().inflate(R.layout.popup_photos, null);

        final float popUpheight = getResources().getDimension(
                R.dimen.keyboard_height);
        changeKeyboardHeight((int) popUpheight);
    }

    private void enablePopup() {
        initPopupRecyclerView();

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

    @Override
    public void setAdapterData(final List<PhotoSquareBase> squares) {
        mPopupSquareAdapter.setSquares(squares);
        mPopupSquareAdapter.notifyDataSetChanged();
        mPopupSquareAdapter.setSquareClickListener(new PopupSquareAdapter.SquareClickListener() {
            @Override
            public void onSquareClick(final int position) {
                mPostPresenter.onPhotoClick(position);
            }
        });
    }

    private void initPopupRecyclerView() {
        mPhotoRecyclerView = popupView.findViewById(R.id.popup_photo_recycler);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2, OrientationHelper.HORIZONTAL, false);
        mPhotoRecyclerView.setLayoutManager(layoutManager);
        mPopupSquareAdapter = new PopupSquareAdapter(this);
        mPhotoRecyclerView.setAdapter(mPopupSquareAdapter);
    }

    private void changeKeyboardHeight(final int height) {
        if (height > 100) {
            keyboardHeight = height;

            ConstraintSet set = new ConstraintSet();
            set.constrainHeight(cover.getId(), keyboardHeight);
            set.constrainWidth(cover.getId(), 0);
            set.connect(cover.getId(), ConstraintSet.BOTTOM, mParentLayout.getId(), ConstraintSet.BOTTOM);
            set.connect(cover.getId(), ConstraintSet.LEFT, mParentLayout.getId(), ConstraintSet.LEFT);
            set.connect(cover.getId(), ConstraintSet.RIGHT, mParentLayout.getId(), ConstraintSet.RIGHT);
            set.setVisibility(cover.getId(), ConstraintSet.GONE);
            set.applyTo(mParentLayout);

            mParentLayout.requestLayout();
        }
    }

    @Override
    public void enableSentButton(final boolean isEnabled) {
        mBottomBar.setSentButtonEnabled(isEnabled);
    }

    @Override
    public void setTextStyle(final TextStyle style) {
        mCustomImageView.setTextStyle(style);
    }

    @Override
    public void showCamera() {
        ContentValues content = new ContentValues();
        content.put(MediaStore.Images.Media.TITLE, "new pic");
        content.put(MediaStore.Images.Media.DESCRIPTION, "from camera");
        photoUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CAMERA);
        }
    }

    @Override
    public void showGallery() {
        Intent getIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        getIntent.setType("image/*");
        startActivityForResult(getIntent, REQUEST_GALLERY);
    }

    @Override
    public void initStickersDialog(final List<Sticker> stickers) {
        mStickerDialog = new StickerDialog(this, R.style.StickerDialogSheet, stickers, new StickerRVAdapter.StickerClickListener() {
            @Override
            public void onStickerClick(final int position) {
                mPostPresenter.onStickerClick(position);
            }
        });

        mStickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialogInterface) {
//                Timber.i("onCancel: visible %b need %b", isKeyBoardVisible, keyboardNeedVisible);
//                if (keyboardNeedVisible) {
//                    forceShowKeyboardIfNeed();
//                    showCover(false);
//                }
            }
        });
    }

    @Override
    public void showStickerDialog() {
//        keyboardNeedVisible = isKeyBoardVisible;
//        showCover(isKeyBoardVisible);
//        hideKeyboardIfNeed();
        mStickerDialog.show();
    }

    @Override
    public void closeStickerDialog() {
        mStickerDialog.cancel();
    }

    @Override
    public void addSticker(final Sticker sticker) {
        Timber.i("addSticker");
        mCustomImageView.setSticker(sticker);
    }

//    private void showCover(boolean isVisible) {
//        Timber.i("showCover: %b", isVisible);
//        if (isVisible) {
//            ConstraintSet set = new ConstraintSet();
//            set.clone(mParentLayout);
//            set.setVisibility(cover.getId(), ConstraintSet.VISIBLE);
//            set.applyTo(mParentLayout);
//        } else {
//            ConstraintSet set = new ConstraintSet();
//            set.clone(mParentLayout);
//            set.setVisibility(cover.getId(), ConstraintSet.GONE);
//            set.applyTo(mParentLayout);
//        }
//    }

    @Override
    public void checkPermission() {
        Timber.i("checkPermission");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Timber.i("checkPermission. read contact not granted!");
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Timber.i("checkPermission, shouldShowRequestPermissionRationale");
                //todo add dialog!
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_STORAGE);
            }
        } else {
            mPostPresenter.permissionOk();
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        Timber.i("onRequestPermissionsResult," +
                " request: %d, perms: [%s], results: [%s]", requestCode, Arrays.toString(permissions), Arrays.toString(grantResults));

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.i("onRequestPermissionsResult permission granted!");
                    mPostPresenter.permissionOk();
                } else {
                    Timber.i("onRequestPermissionsResult. permission not granted");
                    //todo show snackbar
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                                getContentResolver(), photoUri);
                        mCustomImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case REQUEST_GALLERY: {
                photoUri = data.getData();
                try {
                    mCustomImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    //todo add screen!
    @Override
    public void sendImageToWall() {

        Timber.i("sendImageToWall userId %s", getMyId());

        VKUploadImage image = new VKUploadImage(getBitmapFromImageView(), VKImageParameters.jpgImage(1f));
        VKRequest request = VKApi.uploadWallPhotoRequest(image, getMyId(), 0);

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(final VKResponse response) {
                Timber.i("onComplete");
                VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                makePost(new VKAttachments(photoModel), "", getMyId());
            }

            @Override
            public void attemptFailed(final VKRequest request, final int attemptNumber,
                                      final int totalAttempts) {
                Timber.i("attemptFailed");
            }

            @Override
            public void onError(final VKError error) {
                Timber.i("request onError %s", error.toString());
            }
        });
    }

    int getMyId() {
        final VKAccessToken vkAccessToken = VKAccessToken.currentToken();
        return vkAccessToken != null ? Integer.parseInt(vkAccessToken.userId) : 0;
    }

    void makePost(VKAttachments att, String msg, final int ownerId) {
        VKParameters parameters = new VKParameters();
        parameters.put(VKApiConst.OWNER_ID, String.valueOf(ownerId));
        parameters.put(VKApiConst.ATTACHMENTS, att);
        parameters.put(VKApiConst.MESSAGE, msg);
        VKRequest post = VKApi.wall().post(parameters);
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Timber.i("onComplete %s", response);
            }

            @Override
            public void onError(VKError error) {
                Timber.i(" make post onError %s", error);
            }
        });
    }

    public Bitmap getBitmapFromImageView() {
        mCustomImageView.setDrawingCacheEnabled(true);
        mCustomImageView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mCustomImageView.buildDrawingCache();

        if (mCustomImageView.getDrawingCache() == null) return null;

        Bitmap screen = Bitmap.createBitmap(mCustomImageView.getDrawingCache());
        mCustomImageView.setDrawingCacheEnabled(false);
        mCustomImageView.destroyDrawingCache();

        return screen;
    }
}
