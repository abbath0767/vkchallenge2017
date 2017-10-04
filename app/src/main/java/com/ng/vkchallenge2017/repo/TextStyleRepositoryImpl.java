package com.ng.vkchallenge2017.repo;

import com.ng.vkchallenge2017.R;
import com.ng.vkchallenge2017.model.text_style.TextStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikitagusarov on 16.09.17.
 */

public class TextStyleRepositoryImpl implements TextStyleRepository {

    private List<TextStyle> mTextStyles;

    private static TextStyleRepository instance;

    static TextStyleRepository getInstance() {
        if (instance == null) {
            instance = new TextStyleRepositoryImpl();
        }
        return instance;
    }

    private TextStyleRepositoryImpl() {
        mTextStyles = new ArrayList<>();

        init();
    }

    private void init() {
        mTextStyles.add(new TextStyle(android.R.color.transparent, R.color.black_text, R.drawable.cursor_color_first));
        mTextStyles.add(new TextStyle(android.R.color.transparent, R.color.white_text, R.drawable.cursor_color_second));
        mTextStyles.add(new TextStyle(R.color.white_text, R.color.black_text, R.drawable.cursor_color_first));
        mTextStyles.add(new TextStyle(R.color.white_text_background, R.color.white_text, R.drawable.cursor_color_second));
    }

    @Override
    public List<TextStyle> getStyles() {
        return mTextStyles;
    }

    @Override
    public TextStyle getStyle(final int position) {
        return mTextStyles.get(position);
    }
}
