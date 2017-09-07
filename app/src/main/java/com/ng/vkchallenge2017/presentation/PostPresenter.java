package com.ng.vkchallenge2017.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareColourGradient;
import com.ng.vkchallenge2017.model.square.BottomSquareDefault;
import com.ng.vkchallenge2017.repo.SquareRepository;
import com.ng.vkchallenge2017.repo.SquareRepositoryImpl;
import com.ng.vkchallenge2017.view.PostView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitagusarov on 06.09.17.
 */

@InjectViewState
public class PostPresenter extends MvpPresenter<PostView> {

    private SquareRepository mSquareRepository;

    public PostPresenter() {
        mSquareRepository = SquareRepositoryImpl.getInstance();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().setBottomBarRecycler(mSquareRepository.getSquares());
    }
}
