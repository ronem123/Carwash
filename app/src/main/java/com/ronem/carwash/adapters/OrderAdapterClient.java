package com.ronem.carwash.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.model.Order;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.view.OrderMapActivity;
import com.ronem.carwash.viewholders.OrderLiveViewHolderClient;

import java.util.List;

/**
 * Created by ram on 8/12/17.
 */

public class OrderAdapterClient extends RecyclerView.Adapter<OrderLiveViewHolderClient> {
    private List<Order> orders;
    private boolean isLiveOrder;
    private Context context;

    public OrderAdapterClient(List<Order> orders, boolean isLiveOrder) {
        this.orders = orders;
        this.isLiveOrder = isLiveOrder;
    }

    @Override
    public OrderLiveViewHolderClient onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.single_row_live_order_client, parent, false);
        return new OrderLiveViewHolderClient(itemView);
    }

    @Override
    public void onBindViewHolder(OrderLiveViewHolderClient holder, int position) {
        final Order order = orders.get(position);

        if (isLiveOrder) {
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, OrderMapActivity.class);
                    i.putExtra(MetaData.KEY_LATITUDE, order.getLatitude());
                    i.putExtra(MetaData.KEY_LONGITUDE, order.getLongitue());
                    context.startActivity(i);
                }
            });
        } else {
            holder.btnAcceptOrder.setVisibility(View.GONE);
            holder.btnIgnoreOrder.setVisibility(View.GONE);
        }

        holder.customerNameTv.setText(orders.get(position).getCarwarsherName());
        holder.customerServiceTv.setText(orders.get(position).getService());
        holder.btnAcceptOrder
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Order.updateOrderStatus(order.getOrderId(), MetaData.ORDER_STATUS_PROCESSING);
                        orders.remove(order);
                        notifyDataSetChanged();
                    }
                });

        holder.btnIgnoreOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders.remove(order);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
