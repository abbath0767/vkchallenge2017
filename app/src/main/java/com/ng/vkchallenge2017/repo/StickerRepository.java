package com.ng.vkchallenge2017.repo;

import com.ng.vkchallenge2017.model.sticker.Sticker;

import java.util.List;

/**
 * Created by nikitagusarov on 17.09.17.
 */

public interface StickerRepository {
    List<Sticker> getStickers();

    Sticker getSticker(int position);
}
