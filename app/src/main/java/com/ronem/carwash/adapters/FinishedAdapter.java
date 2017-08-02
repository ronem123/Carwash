package com.ronem.carwash.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.viewholders.FinishedViewHOlder;

import java.util.List;

/**
 * Created by ronem on 8/2/17.
 */

public class FinishedAdapter extends RecyclerView.Adapter<FinishedViewHOlder> {
    List<String> ls;

    public FinishedAdapter(List<String> ls) {
        this.ls = ls;
    }

    @Override
    public FinishedViewHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_finished_order_layout, parent, false);

        return new FinishedViewHOlder(v);
    }

    @Override
    public void onBindViewHolder(FinishedViewHOlder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }
}
