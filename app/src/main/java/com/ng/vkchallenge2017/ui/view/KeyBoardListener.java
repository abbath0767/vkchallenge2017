package com.ng.vkchallenge2017.ui.view;

import android.app.Activity;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.ref.WeakReference;

/**
 * Created by nikitagusarov on 12.09.17.
 */

public class KeyBoardListener {

    private static final int KEYBOARD_MIN_HEIGHT = 150;
    private static WeakReference<View> decoderViewWeak;

    public interface KeyBoardMoveListener {
        void isShown(boolean isShowing);
    }

    public static void observeKeyBoard(@NonNull final Activity activity, @NonNull final KeyBoardMoveListener listener) {
        decoderViewWeak = new WeakReference<>(activity.getWindow().getDecorView());
        decoderViewWeak.get().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            final Rect rect = new Rect();
            int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                decoderViewWeak.get().getWindowVisibleDisplayFrame(rect);
                int rectHeight = rect.height();

                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > rectHeight + KEYBOARD_MIN_HEIGHT) {
                        // Calculate current keyboard height (this includes also navigation bar height when in fullscreen mode).
//                        int currentKeyboardHeight = decoderViewWeak.get().getHeight() - rect.bottom;
                        // Notify listener about keyboard being shown.
                        listener.isShown(true);
                    } else if (lastVisibleDecorViewHeight + KEYBOARD_MIN_HEIGHT < rectHeight) {
                        // Notify listener about keyboard being hidden.
                        listener.isShown(false);
                    }
                }
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = rectHeight;
            }
        });

    }
}
