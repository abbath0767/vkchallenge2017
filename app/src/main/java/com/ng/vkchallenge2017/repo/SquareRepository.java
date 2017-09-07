package com.ng.vkchallenge2017.repo;

import com.ng.vkchallenge2017.model.square.BottomSquareBase;

import java.util.List;

/**
 * Created by nikitagusarov on 07.09.17.
 */

public interface SquareRepository {
    List<BottomSquareBase> getSquares();
}
