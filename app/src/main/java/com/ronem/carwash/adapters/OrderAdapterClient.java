package com.ronem.carwash.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.model.Order;
import com.ronem.carwash.utils.EventBus;
import com.ronem.carwash.utils.Events;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.view.OrderMapActivity;
import com.ronem.carwash.viewholders.OrderLiveViewHolderClient;

import java.util.List;

/**
 * Created by ram on 8/12/17.
 */

public class OrderAdapterClient extends RecyclerView.Adapter<OrderLiveViewHolderClient> {
    private List<Order> orders;
    private String status;
    private Context context;

    public OrderAdapterClient(List<Order> orders, String status) {
        this.orders = orders;
        this.status = status;
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

        switch (status) {
            case MetaData.ORDER_STATUS_LIVE:
                holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, OrderMapActivity.class);
                        i.putExtra(MetaData.KEY_LATITUDE, order.getLatitude());
                        i.putExtra(MetaData.KEY_LONGITUDE, order.getLongitue());
                        context.startActivity(i);
                    }
                });
                break;
            case MetaData.ORDER_STATUS_PROCESSING:
                holder.btnAcceptOrder.setVisibility(View.GONE);
                holder.btnViewDetail.setVisibility(View.GONE);
                holder.btnIgnoreOrder.setVisibility(View.GONE);
                holder.processView.setVisibility(View.VISIBLE);
                holder.btnCompleteOrder.setVisibility(View.VISIBLE);

                break;
            case MetaData.ORDER_STATUS_FINISHED:
                holder.btnAcceptOrder.setVisibility(View.GONE);
                holder.btnIgnoreOrder.setVisibility(View.GONE);
                holder.btnViewDetail.setVisibility(View.GONE);
                holder.processView.setVisibility(View.GONE);
                holder.checkImage.setVisibility(View.VISIBLE);
                break;
        }

        holder.customerNameTv.setText(orders.get(position).getCustomerName());
        holder.customerServiceTv.setText(orders.get(position).getService());
        holder.btnAcceptOrder
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Order.updateOrderStatus(order.getOrderId(), MetaData.ORDER_STATUS_PROCESSING);
                        orders.remove(order);
                        notifyDataSetChanged();
                        EventBus.post(new Events.ReloadEvent(true));
                    }
                });

        holder.btnIgnoreOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order.remove(order.getOrderId());
                orders.remove(order);
                notifyDataSetChanged();
            }
        });

        holder.btnCompleteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order.updateOrderStatus(order.getOrderId(), MetaData.ORDER_STATUS_FINISHED);
                orders.remove(order);
                notifyDataSetChanged();
            }
        });

        holder.btnViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message =
                        "Carwasher : " + order.getCarwarsherName() +
                                "\nCarwasher Email : " + order.getCarwasherEmail() +
                                "\nCarwasher Contact : " + order.getCarWasherContact() +
                                "\nCar Type : " + order.getCarType() +
                                "\nService : " + order.getService() +
                                "\nTotal Cost : " + order.getPayedAmount();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Order made by " + order.getCustomerName());
                builder.setMessage(message);
                builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
