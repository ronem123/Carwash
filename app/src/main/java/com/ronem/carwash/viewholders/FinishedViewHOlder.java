package com.ronem.carwash.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by ronem on 8/2/17.
 */

public class FinishedViewHOlder extends RecyclerView.ViewHolder {
    public FinishedViewHOlder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
