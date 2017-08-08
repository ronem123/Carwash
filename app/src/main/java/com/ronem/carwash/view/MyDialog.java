package com.ronem.carwash.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.ronem.carwash.R;
import com.ronem.carwash.model.DeliveredStationLocation;
import com.ronem.carwash.utils.DistanceCalculator;
import com.ronem.carwash.utils.MetaData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 8/2/17.
 */

public class MyDialog extends Dialog {
    @Bind(R.id.distance)
    TextView distanceView;
    @Bind(R.id.time_to_reach)
    TextView timeToReach;
    @Bind(R.id.time_to_reach_location)
    TextView location;
    @Bind(R.id.car_washer)
    TextView carWasherV;

    private DeliveredStationLocation deliveredStationLocation;
    private String distance;
    private String duration;
    private String orderType;

    public MyDialog(Context context, String distance, String duration, DeliveredStationLocation deliveredStationLocation,String orderType) {
        super(context, R.style.slideAnimation);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.my_dialog);
        ButterKnife.bind(this);

        this.deliveredStationLocation = deliveredStationLocation;
        this.distance = distance;
        this.duration = duration;
        this.orderType = orderType;

        distanceView.setText(distance);
        timeToReach.setText(duration);

        location.setText(deliveredStationLocation.getAddress());
        carWasherV.setText(deliveredStationLocation.getCarWasher());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.dialog_btn_show_more)
    public void onDialogBtnShowMoreClicked() {
        Intent i = new Intent(getContext(), ShowDetailActivity.class);
        i.putExtra(MetaData.KEY_ADDRESS, deliveredStationLocation);
        i.putExtra(MetaData.KEY_ORDER_TYPE,orderType);
        i.putExtra(MetaData.KEY_DISTANCE, distance);
        i.putExtra(MetaData.KEY_DURATION, duration);
        getContext().startActivity(i);
        dismiss();
    }

    @OnClick(R.id.dialog_btn_close)
    public void onDialogBtnCloseClicked() {
        dismiss();
    }
}
