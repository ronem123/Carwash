package com.ronem.carwash.view.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.OrderAdapter;
import com.ronem.carwash.model.Order;
import com.ronem.carwash.utils.MetaData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ronem on 8/2/17.
 */

public class FragmentLiveOrder extends Fragment {
    @Bind(R.id.order_recycler_view)
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_live_order, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new OrderAdapter(Order.getOrders()));
    }
}
