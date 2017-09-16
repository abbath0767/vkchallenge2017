package com.ng.vkchallenge2017.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ng.vkchallenge2017.repo.PhotoRepository;
import com.ng.vkchallenge2017.repo.SquareRepository;
import com.ng.vkchallenge2017.repo.SquareRepositoryImpl;
import com.ng.vkchallenge2017.repo.TextStyleContainer;
import com.ng.vkchallenge2017.repo.TextStyleContainerImpl;
import com.ng.vkchallenge2017.view.PostView;

import timber.log.Timber;

import static com.ng.vkchallenge2017.presentation.PostPresenter.Mode.HISTORY;
import static com.ng.vkchallenge2017.presentation.PostPresenter.Mode.POST;
import static com.ng.vkchallenge2017.repo.PhotoRepositoryImpl.MAX_SQUARE_COUNT;

/**
 * Created by nikitagusarov on 06.09.17.
 */

@InjectViewState
public class PostPresenter extends MvpPresenter<PostView> {

    private SquareRepository mSquareRepository;
    private PhotoRepository mPhotoRepository;
    private TextStyleContainer mTextStyleContainer;
    private Mode mMode = POST;
    private boolean loaded = false;

    private int photoPosition = 2;
    private int textStyleNum = 0;

    public PostPresenter(PhotoRepository photoRepository) {
        mSquareRepository = SquareRepositoryImpl.getInstance();
        mPhotoRepository = photoRepository;
        mTextStyleContainer = TextStyleContainerImpl.getInstance();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setBottomBarRecycler(mSquareRepository.getSquares());
        getViewState().setAdapterData(mPhotoRepository.getPhotoSquares());
        getViewState().setTextStyle(mTextStyleContainer.getStyle(textStyleNum));
    }

    public void onSquareClick(final int position) {
        Timber.i("onSquareClick, pos: %d", position);
        if (position != mSquareRepository.getSquares().size() - 1) {
            getViewState().disablePopup();
            getViewState().setUpContent(mSquareRepository.getSquares().get(position));
        } else {
            Timber.i("onSquareClick %b, %d", loaded, photoPosition);
            getViewState().setUpPopup();

            if (!loaded)
                getViewState().checkPermission();
            else {
                getViewState().setSelectedPhotoPosition(photoPosition);
                getViewState().setUpPhoto(mPhotoRepository.getPhoto(photoPosition));
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

        getViewState().setUpMode(mMode);
    }

    private void loadPhoto() {
        if (mPhotoRepository.getPhotoSquares().size() != MAX_SQUARE_COUNT || !loaded) {
            mPhotoRepository.loadPhotoFromGallery();
            loaded = true;

            getViewState().setSelectedPhotoPosition(photoPosition);
            getViewState().setUpPhoto(mPhotoRepository.getPhoto(photoPosition));
        }
    }

    public void textChange(final String text) {
        if (text.isEmpty()) {
            getViewState().enableSentButton(false);
        } else {
            getViewState().enableSentButton(true);

        }
    }

    public void permissionOk() {
        loadPhoto();
    }

    public void onPhotoClick(final int position) {
        if (position > 1) {
            photoPosition = position;
            getViewState().setUpPhoto(mPhotoRepository.getPhoto(position));
        } else if (position == 0) {
            getViewState().showCamera();
        } else if (position == 1) {
            getViewState().showGallery();
        }
    }

    public void onButtonLeftClick() {
        int nextStyleNum = nextStyleNum();
        Timber.i("onButtonLeftClick : %d ", nextStyleNum);
        getViewState().setTextStyle(mTextStyleContainer.getStyle(nextStyleNum));
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

    public enum Mode {
        POST, HISTORY
    }
}
