package com.ng.vkchallenge2017.repo;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.square.BottomSquareAction;
import com.ng.vkchallenge2017.model.square.BottomSquareBase;
import com.ng.vkchallenge2017.model.square.BottomSquareColourGradient;
import com.ng.vkchallenge2017.model.square.BottomSquareDefault;
import com.ng.vkchallenge2017.model.square.BottomSquareThumb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public class SquareRepositoryImpl implements SquareRepository{

    @Override
    public List<BottomSquareBase> getSquares() {
        List<BottomSquareBase> list = new ArrayList<>();

        list.add(new BottomSquareDefault(R.color.gray_square));

        list.add(new BottomSquareColourGradient(R.color.blue_square_start, R.color.blue_square_end));
        list.add(new BottomSquareColourGradient(R.color.green_square_start, R.color.green_square_end));
        list.add(new BottomSquareColourGradient(R.color.yellow_square_start, R.color.yellow_square_end));
        list.add(new BottomSquareColourGradient(R.color.pink_square_start, R.color.pink_square_end));
        list.add(new BottomSquareColourGradient(R.color.violet_square_start, R.color.pink_square_end));

        list.add(new BottomSquareThumb(R.drawable.thumb_beach));
        list.add(new BottomSquareThumb(R.drawable.thumb_stars));

        list.add(new BottomSquareAction(R.drawable.ic_toolbar_new));

        return list;
    }
}
