package com.ng.vkchallenge2017.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nikitagusarov on 06.09.17.
 */

public class BottomSquareRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull private Context mContext;

    BottomSquareRVAdapter(@NonNull final Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private static class SquareViewHolder extends RecyclerView.ViewHolder {

        public SquareViewHolder(final View itemView) {
            super(itemView);
        }
    }
}
