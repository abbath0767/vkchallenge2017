package com.ng.vkchallenge2017.presentation;

import android.support.annotation.NonNull;

import com.ng.vkchallenge2017.repo.PhotoRepository;
import com.ng.vkchallenge2017.repo.SquareRepository;
import com.ng.vkchallenge2017.repo.SquareRepositoryImpl;
import com.ng.vkchallenge2017.repo.StickerRepository;
import com.ng.vkchallenge2017.repo.TextStyleContainer;
import com.ng.vkchallenge2017.repo.TextStyleContainerImpl;
import com.ng.vkchallenge2017.view.PostViewContract;

import timber.log.Timber;

import static com.ng.vkchallenge2017.presentation.PostPresenter.Mode.HISTORY;
import static com.ng.vkchallenge2017.presentation.PostPresenter.Mode.POST;
import static com.ng.vkchallenge2017.repo.PhotoRepositoryImpl.MAX_SQUARE_COUNT;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class PostPresenter implements PostViewContract.Presenter {

    private final PostViewContract.View mView;
    private SquareRepository mSquareRepository;
    private PhotoRepository mPhotoRepository;
    private TextStyleContainer mTextStyleContainer;
    private StickerRepository mStickersRepository;
    private Mode mMode = POST;
    private boolean loaded = false;

    private int photoPosition = 2;
    private int textStyleNum = 0;

    public PostPresenter(@NonNull final PostViewContract.View postActivity,
                         @NonNull final PhotoRepository photoRepository,
                         @NonNull final StickerRepository stickerRepository) {
        mView = postActivity;
        mPhotoRepository = photoRepository;
        mStickersRepository = stickerRepository;
        mSquareRepository = SquareRepositoryImpl.getInstance();
        mTextStyleContainer = TextStyleContainerImpl.getInstance();

        mView.setBottomBarRecycler(mSquareRepository.getSquares());
        mView.setAdapterData(mPhotoRepository.getPhotoSquares());
        mView.setTextStyle(mTextStyleContainer.getStyle(textStyleNum));
        mView.initStickersDialog(mStickersRepository.getStickers());
    }
    
    public void onSquareClick(final int position) {
        Timber.i("onSquareClick, pos: %d", position);
        if (position != mSquareRepository.getSquares().size() - 1) {
            mView.disablePopup();
            mView.setUpContent(mSquareRepository.getSquares().get(position));
        } else {
            Timber.i("onSquareClick %b, %d", loaded, photoPosition);
            mView.setUpPopup();

            if (!loaded)
                mView.checkPermission();
            else {
                mView.setSelectedPhotoPosition(photoPosition);
                mView.setUpPhoto(mPhotoRepository.getPhoto(photoPosition));
            }
        }
    }

    public void tabSelected(final int position) {
        Timber.i("tabSelected %d", position);
        if (position == 0) {
            mMode = POST;
        } else {
            mMode = HISTORY;
        }

        mView.setUpMode(mMode);
    }

    private void loadPhoto() {
        if (mPhotoRepository.getPhotoSquares().size() != MAX_SQUARE_COUNT || !loaded) {
            mPhotoRepository.loadPhotoFromGallery();
            loaded = true;

            mView.setSelectedPhotoPosition(photoPosition);
            mView.setUpPhoto(mPhotoRepository.getPhoto(photoPosition));
        }
    }

    public void textChange(final String text) {
        if (text.isEmpty()) {
            mView.enableSentButton(false);
        } else {
            mView.enableSentButton(true);

        }
    }

    public void permissionOk() {
        loadPhoto();
    }

    public void onPhotoClick(final int position) {
        if (position > 1) {
            photoPosition = position;
            mView.setUpPhoto(mPhotoRepository.getPhoto(position));
        } else if (position == 0) {
            mView.showCamera();
        } else if (position == 1) {
            mView.showGallery();
        }
    }

    public void onButtonLeftClick() {
        int nextStyleNum = nextStyleNum();
        mView.setTextStyle(mTextStyleContainer.getStyle(nextStyleNum));
    }

    private int nextStyleNum() {
        int max = mTextStyleContainer.getStyles().size() - 1;
        if (textStyleNum == max) {
            textStyleNum = 0;
            return textStyleNum;
        } else {
            textStyleNum += 1;
            return textStyleNum;
        }
    }

    public void onButtonRightClick() {
        mView.showStickerDialog();
    }

    public void onStickerClick(final int position) {
        Timber.i("onStickerClick: pos: %d", position);
        mView.closeStickerDialog();
        mView.addSticker(mStickersRepository.getSticker(position));
    }

    public void sendClick() {
        mView.sendImageToWall();
    }

    public enum Mode {
        POST, HISTORY
    }
}
