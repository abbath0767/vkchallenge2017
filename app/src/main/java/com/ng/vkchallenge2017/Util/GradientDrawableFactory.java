package com.ng.vkchallenge2017.Util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;

import com.ng.vkchallenge2017.BuildConfig;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class GradientDrawableFactory {

    public static GradientDrawable getGradient(@NonNull int startColor, @NonNull int endColor, @NonNull GradientDrawable.Orientation orientation, @NonNull Context context) {
        GradientDrawable gradientDrawable;

        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.M) {
            gradientDrawable =
                    new GradientDrawable(orientation,
                            new int[]{context.getResources().getColor(startColor), context.getResources().getColor(endColor)});
        } else {
            gradientDrawable = new GradientDrawable(orientation, new int[]{context.getColor(startColor), context.getColor(endColor)});
        }

        gradientDrawable.setCornerRadius(0F);

        return gradientDrawable;
    }
}
