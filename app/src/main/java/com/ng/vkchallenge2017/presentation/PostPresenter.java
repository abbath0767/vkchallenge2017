package com.ng.vkchallenge2017.presentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareDefault;
import com.ng.vkchallenge2017.view.PostView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitagusarov on 06.09.17.
 */

@InjectViewState
public class PostPresenter extends MvpPresenter<PostView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        initAndShowBottomBarRecycler();
    }

    private void initAndShowBottomBarRecycler() {
        List<BottomSquareBase> squares = generateSquares();


    }

    private List<BottomSquareBase> generateSquares() {
        List<BottomSquareBase> list = new ArrayList<>();

        list.add(new BottomSquareDefault(R.color.gray_square));
        list.add()
    }
}
