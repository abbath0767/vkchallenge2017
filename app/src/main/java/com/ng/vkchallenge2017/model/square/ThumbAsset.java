package com.ng.vkchallenge2017.model.square;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitagusarov on 12.09.17.
 */

public class ThumbAsset extends Thumb<List<Integer>> {
    @NonNull private final int topThumb;
    @NonNull private final int midThumb;
    @NonNull private final int botThumb;
    @NonNull private final List<Integer> mList;

    public ThumbAsset(@NonNull final int topThumb, @NonNull final int midThumb, @NonNull final int botThumb) {
        this.topThumb = topThumb;
        this.midThumb = midThumb;
        this.botThumb = botThumb;
        mList = new ArrayList<>();
        initList();
    }

    private void initList() {
        mList.add(topThumb);
        mList.add(midThumb);
        mList.add(botThumb);
    }

    @Override
    public List<Integer> getResource() {
        return mList;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ThumbAsset that = (ThumbAsset) o;

        if (topThumb != that.topThumb) return false;
        if (midThumb != that.midThumb) return false;
        return botThumb == that.botThumb;
    }

    @Override
    public int hashCode() {
        int result = topThumb;
        result = 31 * result + midThumb;
        result = 31 * result + botThumb;
        return result;
    }
}
