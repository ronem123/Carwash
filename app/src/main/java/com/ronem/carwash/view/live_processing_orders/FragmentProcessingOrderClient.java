package com.ronem.carwash.view.live_processing_orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.OrderAdapterClient;
import com.ronem.carwash.model.Order;
import com.ronem.carwash.utils.EventBus;
import com.ronem.carwash.utils.Events;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.RecyclerItemClickListener;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ram on 8/12/17.
 */

public class FragmentProcessingOrderClient extends Fragment {

    @Bind(R.id.order_recycler_view)
    RecyclerView recyclerView;

    private List<Order> orders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_live_order, container, false);
        ButterKnife.bind(this, root);
        EventBus.register(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.hasFixedSize();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        orders = Order.getOrders(MetaData.ORDER_STATUS_PROCESSING);
        recyclerView.setAdapter(new OrderAdapterClient(orders, MetaData.ORDER_STATUS_PROCESSING));
    }

    @Subscribe
    public void onReloadDataReceived(Events.ReloadEvent event) {
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.unregister(this);
    }
}
