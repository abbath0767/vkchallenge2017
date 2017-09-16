package com.ng.vkchallenge2017.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.presentation.PostPresenter;

import java.util.List;

/**
 * Created by nikitagusarov on 06.09.17.
 */

@StateStrategyType(SingleStateStrategy.class)
public interface PostView extends MvpView {

    void setBottomBarRecycler(List<BottomSquareBase> squares);

    void setUpContent(BottomSquareBase squareBase);

    void setUpMode(PostPresenter.Mode mode);

    void setUpPopup();

    void disablePopup();

    void setAdapterData(List<PhotoSquareBase> squares);

    void checkPermission();

    void enableSentButton(boolean isEnabled);

    void setUpPhoto(final PhotoSquareBase photo);
}
