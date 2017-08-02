package com.ronem.carwash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.model.PaymentMethod;
import com.ronem.carwash.viewholders.PaymentViewHolder;

import java.util.List;

/**
 * Created by ronem on 8/2/17.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentViewHolder> {
    private List<PaymentMethod> paymentMethods;
    private Context context;

    public PaymentAdapter(List<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.payment_method, parent, false);
        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, int position) {
        PaymentMethod p = paymentMethods.get(position);
        holder.paymentMethodIcon.setImageResource(p.getIcon());
        holder.paymentText.setText(p.getPaymentMethod());

        if (p.isSelected()) {
            holder.checkBox.setImageResource(R.mipmap.ic_check_box_black_24dp);
        } else {
            holder.checkBox.setImageResource(R.mipmap.ic_check_box_outline_blank_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return paymentMethods.size();
    }
}
