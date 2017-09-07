package com.ng.vkchallenge2017.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;

import java.util.List;

/**
 * Created by nikitagusarov on 06.09.17.
 */

@StateStrategyType(SingleStateStrategy.class)
public interface PostView extends MvpView {

    void setBottomBarRecycler(List<BottomSquareBase> squares);
}
