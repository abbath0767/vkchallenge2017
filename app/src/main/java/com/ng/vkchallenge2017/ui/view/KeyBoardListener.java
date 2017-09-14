package com.ng.vkchallenge2017.ui.view;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;

import timber.log.Timber;

/**
 * Created by nikitagusarov on 12.09.17.
 */

public class KeyBoardListener {

    public static final int KEYBOARD_MIN_HEIGHT = 150;
    private static WeakReference<View> decoderViewWeak;

    public interface KeyBoardMoveListener {
        void isShown(boolean isShowing, int hightDifference);
    }

    private static int previousHeightDiffrence = 0;

    public static void observeKeyBoard(@NonNull final ConstraintLayout parentLayout,
                                       @NonNull final PopupWindow popupWindow,
                                       @NonNull final KeyBoardMoveListener listener) {

        parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        parentLayout.getWindowVisibleDisplayFrame(r);

                        int screenHeight = parentLayout.getRootView()
                                .getHeight();

                        int heightDifference = screenHeight - (r.bottom);

                        if (previousHeightDiffrence - heightDifference > 50) {
                                popupWindow.dismiss();
                        }

                        previousHeightDiffrence = heightDifference;
                        if (heightDifference > 100) {

                            listener.isShown(true, heightDifference);

                        } else {

                            listener.isShown(false, 0);
                        }
                    }
                });
    }
}

