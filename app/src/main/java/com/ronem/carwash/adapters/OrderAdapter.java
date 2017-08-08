package com.ronem.carwash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.model.Order;
import com.ronem.carwash.viewholders.OrderViewHolder;

import java.util.List;

/**
 * Created by ronem on 8/8/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {
    List<Order> orders;
    private Context context;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.single_row_order, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order o = orders.get(position);
        holder.carwasherNameV.setText(o.getCarwarsherName());
        holder.carwasherContact.setText(o.getCarWasherContact());
        holder.carwasherAddressV.setText(o.getMyAddress());
        holder.vehicleTypeView.setText(context.getString(R.string.vehicle_type_s,o.getCarType()));
        holder.serviceTypeView.setText(context.getString(R.string.service_type_s,o.getService()));
        holder.paymentMethodView.setText(o.getPaymentMethod());
        holder.payedAmountView.setText(o.getPayedAmount());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
