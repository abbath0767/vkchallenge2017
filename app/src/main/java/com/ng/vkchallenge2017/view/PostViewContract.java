package com.ng.vkchallenge2017.view;

import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.sticker.Sticker;
import com.ng.vkchallenge2017.model.text_style.TextStyle;
import com.ng.vkchallenge2017.presentation.PostPresenter;

import java.util.List;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public interface PostViewContract {

    interface View{
        void setBottomBarRecycler(List<BottomSquareBase> squares);

        void setUpContent(BottomSquareBase squareBase);

        void setUpMode(PostPresenter.Mode mode);

        void setUpPopup();

        void disablePopup();

        void setAdapterData(List<PhotoSquareBase> squares);

        void checkPermission();

        void enableSentButton(boolean isEnabled);

        void setUpPhoto(final PhotoSquareBase photo);

        void setSelectedPhotoPosition(final int photoPosition);

        void showCamera();

        void showGallery();

        void setTextStyle(TextStyle style);

        void showStickerDialog();

        void initStickersDialog(final List<Sticker> stickers);

        void closeStickerDialog();

        void addSticker(Sticker sticker);

        void sendImageToWall();
    }

    interface Presenter{

        void onButtonLeftClick();

        void onButtonRightClick();

        void onSquareClick(int position);

        void sendClick();

        void tabSelected(int position);

        void textChange(String s);

        void onPhotoClick(int position);

        void onStickerClick(int position);

        void permissionOk();
    }
}
