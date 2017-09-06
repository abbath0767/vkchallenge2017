package com.ng.vkchallenge2017.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by nikitagusarov on 06.09.17.
 */

@StateStrategyType(SingleStateStrategy.class)
public interface MainView extends MvpView{
    void makeLoginCall();

    void showPostActivity();
}
