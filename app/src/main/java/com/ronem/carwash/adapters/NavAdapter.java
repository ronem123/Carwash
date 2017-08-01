package com.ronem.carwash.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.model.NavItem;
import com.ronem.carwash.viewholders.NavViewHolder;

/**
 * Created by ronem on 8/1/17.
 */

public class NavAdapter extends RecyclerView.Adapter<NavViewHolder> {
    private NavItem[] items;

    public NavAdapter(NavItem[] items) {

        this.items = items;
    }

    @Override
    public NavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_nav_item, parent, false);
        return new NavViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NavViewHolder holder, int position) {
        holder.icon.setImageResource(items[position].getIcon());
        holder.title.setText(items[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
