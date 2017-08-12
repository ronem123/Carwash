package com.ronem.carwash.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ronem.carwash.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ram on 8/12/17.
 */

public class OrderLiveViewHolderClient extends RecyclerView.ViewHolder {
    @Bind(R.id.customer_name_service_layout)public LinearLayout parentLayout;
    @Bind(R.id.customer_name_tv)
    public TextView customerNameTv;
    @Bind(R.id.customer_service_tv)
    public TextView customerServiceTv;
    @Bind(R.id.btn_accept_order)
    public TextView btnAcceptOrder;
    @Bind(R.id.btn_ignore_order)
    public TextView btnIgnoreOrder;

    public OrderLiveViewHolderClient(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
