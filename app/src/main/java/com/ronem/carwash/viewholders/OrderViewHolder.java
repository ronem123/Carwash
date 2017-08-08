package com.ronem.carwash.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ronem.carwash.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ronem on 8/8/17.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.car_washer_name)
    public TextView carwasherNameV;
    @Bind(R.id.car_washer_contact)
    public TextView carwasherContact;
    @Bind(R.id.vehicle_type)
    public TextView vehicleTypeView;
    @Bind(R.id.service_type)
    public TextView serviceTypeView;
    @Bind(R.id.payment_method)
    public TextView paymentMethodView;
    @Bind(R.id.payed_amount)
    public TextView payedAmountView;
    @Bind(R.id.car_washer_address)
    public TextView carwasherAddressV;

    public OrderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
