package com.ng.vkchallenge2017.repo;

import com.ng.vkchallenge2017.model.photo.PhotoSquareBase;

import java.util.List;

/**
 * Created by nikitagusarov on 14.09.17.
 */

public interface PhotoRepository {
    List<PhotoSquareBase> getPhotoSquares();
}
