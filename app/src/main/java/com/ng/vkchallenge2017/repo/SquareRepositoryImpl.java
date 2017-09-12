package com.ng.vkchallenge2017.repo;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.square.BottomSquareAction;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareColourGradient;
import com.ng.vkchallenge2017.model.square.BottomSquareDefault;
import com.ng.vkchallenge2017.model.square.BottomSquareThumb;
import com.ng.vkchallenge2017.model.square.SimpleThumb;
import com.ng.vkchallenge2017.model.square.ThumbAsset;
import com.ng.vkchallenge2017.model.square.Thumbs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public class SquareRepositoryImpl implements SquareRepository{

    private static SquareRepository instance;
    public static SquareRepository getInstance() {
        if (instance == null) {
            instance = new SquareRepositoryImpl();
        }
        return instance;
    }

    private SquareRepositoryImpl() {
        initSquares();
    }

    private List<BottomSquareBase> mSquares;

    private void initSquares() {
        mSquares = new ArrayList<>();

        mSquares.add(new BottomSquareDefault(R.color.gray_square));

        mSquares.add(new BottomSquareColourGradient(R.color.blue_square_start, R.color.blue_square_end));
        mSquares.add(new BottomSquareColourGradient(R.color.green_square_start, R.color.green_square_end));
        mSquares.add(new BottomSquareColourGradient(R.color.yellow_square_start, R.color.yellow_square_end));
        mSquares.add(new BottomSquareColourGradient(R.color.pink_square_start, R.color.pink_square_end));
        mSquares.add(new BottomSquareColourGradient(R.color.violet_square_start, R.color.pink_square_end));

        mSquares.add(new BottomSquareThumb(new Thumbs(R.drawable.thumb_beach,
                new ThumbAsset(R.mipmap.bg_beach_top, R.mipmap.bg_beach_center, R.mipmap.bg_beach_bottom))));
        mSquares.add(new BottomSquareThumb(new Thumbs(R.drawable.thumb_stars,
                new SimpleThumb(R.mipmap.bg_stars_center))));

        mSquares.add(new BottomSquareAction(R.drawable.ic_toolbar_new));
    }

    @Override
    public List<BottomSquareBase> getSquares() {
        return mSquares;
    }
}
