package com.ng.vkchallenge2017.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareColourGradient;
import com.ng.vkchallenge2017.model.square.BottomSquareDefault;
import com.ng.vkchallenge2017.repo.PhotoRepository;
import com.ng.vkchallenge2017.repo.SquareRepository;
import com.ng.vkchallenge2017.repo.SquareRepositoryImpl;
import com.ng.vkchallenge2017.view.PostView;

import java.util.ArrayList;
import java.util.List;

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
    private Mode mMode = POST;
    private boolean loaded = false;

    public PostPresenter(PhotoRepository photoRepository) {
        mSquareRepository = SquareRepositoryImpl.getInstance();
        mPhotoRepository = photoRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setBottomBarRecycler(mSquareRepository.getSquares());
        getViewState().setAdapterData(mPhotoRepository.getPhotoSquares());
    }

    public void onSquareClick(final int position) {
        Timber.i("onSquareClick, pos: %d", position);
        if (position != mSquareRepository.getSquares().size() - 1) {
            getViewState().disablePopup();
            getViewState().setUpContent(mSquareRepository.getSquares().get(position));
        } else {
            getViewState().setUpPopup();
            if (!loaded)
                getViewState().checkPermission();
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

    public void loadPhoto() {
        Timber.i("loadPhoto");
        if (mPhotoRepository.getPhotoSquares().size() != MAX_SQUARE_COUNT || !loaded) {
            Timber.i("loadPhoto need load");
            mPhotoRepository.loadPhotoFromGallery();
            loaded = true;
        }
    }

    public void textChange(final String text) {
        if (text.isEmpty()) {
            Timber.i("textChange: text is empty!");
            getViewState().enableSentButton(false);
        } else {
            Timber.i("textChange: text: \"%s\".", text);
            getViewState().enableSentButton(true);

        }
    }

    public enum Mode {
        POST, HISTORY
    }
}
