package com.ronem.carwash.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronem.carwash.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ronem on 8/2/17.
 */

public class PaymentViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.checkbox)
    public ImageView checkBox;
    @Bind(R.id.payment_method_icon)
    public ImageView paymentMethodIcon;
    @Bind(R.id.payment_text)
    public TextView paymentText;

    public PaymentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
