package com.ng.vkchallenge2017.repo;

import com.ng.vkchallenge2017.model.text_style.TextStyle;

import java.util.List;

/**
 * Created by nikitagusarov on 16.09.17.
 */

public interface TextStyleRepository {
    List<TextStyle> getStyles();
    TextStyle getStyle(int position);
}
