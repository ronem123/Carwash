package com.ronem.carwash.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronem.carwash.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ronem on 8/1/17.
 */

public class NavViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.icon)
    public ImageView icon;
    @Bind(R.id.carWasher)
    public TextView title;

    public NavViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
