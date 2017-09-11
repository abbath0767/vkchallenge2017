package com.ng.vkchallenge2017.Util;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by nikitagusarov on 12.09.17.
 */

public class KeyBoardListenerSupport {
    static int mAppHeight;

    public static void setKeyBoardListener(final Activity activity, final KeyBoardListener listener) {
        final View root = activity.findViewById(android.R.id.content);

        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mPreviousHeight;

            @Override
            public void onGlobalLayout() {

                int newHeight = root.getHeight();
                if (newHeight == mPreviousHeight)
                    return;

                if (newHeight >= mAppHeight) {
                    mAppHeight = newHeight;
                }

                if (newHeight != 0) {
                    if (mAppHeight > newHeight) {
                        // Height decreased: keyboard was shown
                        listener.keyBoardStateChange(true);
                    } else {
                        // Height increased: keyboard was hidden
                        listener.keyBoardStateChange(false);
                    }
                }
            }
        });
    }
}