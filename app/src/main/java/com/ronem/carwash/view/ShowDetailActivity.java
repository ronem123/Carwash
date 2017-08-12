package com.ronem.carwash.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.AddressAdapter;
import com.ronem.carwash.adapters.CarTypeAdapter;
import com.ronem.carwash.adapters.PaymentAdapter;
import com.ronem.carwash.adapters.ServiceTypeAdapter;
import com.ronem.carwash.model.Address;
import com.ronem.carwash.model.CarType;
import com.ronem.carwash.model.DeliveredStationLocation;
import com.ronem.carwash.model.Order;
import com.ronem.carwash.model.PaymentMethod;
import com.ronem.carwash.model.ServiceType;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.RecyclerItemClickListener;
import com.ronem.carwash.utils.SessionManager;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 8/2/17.
 */

public class ShowDetailActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, RecyclerItemClickListener.OnItemClickListener {

    @Bind(R.id.show_more_distance)
    TextView distanceView;
    @Bind(R.id.show_more_estimation_time)
    TextView timeView;
    @Bind(R.id.car_washer_name)
    TextView carwasherNameView;
    @Bind(R.id.car_washer_contact)
    TextView contactView;
    @Bind(R.id.rating)
    RatingBar ratingBar;

    @Bind(R.id.spinner_car_type)
    Spinner spinnerCarType;
    @Bind(R.id.spinner_car_washer_address)
    Spinner spinnerAddress;
    @Bind(R.id.spinner_service_type)
    Spinner spinnerServiceTYpe;
    @Bind(R.id.payment_recycler_view)
    RecyclerView paymentREcyclerView;


    private List<CarType> carTypes;
    private List<Address> addresses;
    private List<ServiceType> serviceTypes;

    private CarType carType;
    private Address address;
    private ServiceType serviceTye;
    private String price;
    private String paymentMethod;
    private List<PaymentMethod> paymentMethods;
    private SessionManager sessionManager;

    private DeliveredStationLocation deliveredStationLocation;
    private String distance;
    private String time;
    private String orderType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_detail_activity);
        ButterKnife.bind(this);

        deliveredStationLocation = getIntent().getParcelableExtra(MetaData.KEY_ADDRESS);
        distance = getIntent().getStringExtra(MetaData.KEY_DISTANCE);
        time = getIntent().getStringExtra(MetaData.KEY_DURATION);
        orderType = getIntent().getStringExtra(MetaData.KEY_ORDER_TYPE);
        setIntetnData();

        sessionManager = new SessionManager(this);
        carTypes = MetaData.getCarType();
        addresses = MetaData.getAddress();
        serviceTypes = MetaData.getServiceType();
        paymentMethods = MetaData.getpaymentMEthods();

        spinnerCarType.setAdapter(new CarTypeAdapter(carTypes, this));
        spinnerAddress.setAdapter(new AddressAdapter(addresses, this));
        spinnerServiceTYpe.setAdapter(new ServiceTypeAdapter(serviceTypes, this));

        spinnerCarType.setOnItemSelectedListener(this);
        spinnerAddress.setOnItemSelectedListener(this);
        spinnerServiceTYpe.setOnItemSelectedListener(this);
        paymentREcyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentREcyclerView.setHasFixedSize(true);
        paymentREcyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        paymentREcyclerView.setAdapter(new PaymentAdapter(paymentMethods));

    }

    private void setIntetnData() {
        distanceView.setText(distance);
        timeView.setText(time);
        carwasherNameView.setText(deliveredStationLocation.getCarWasher());
        contactView.setText(deliveredStationLocation.getContact());
        ratingBar.setNumStars(deliveredStationLocation.getRating());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner_car_type:
                carType = carTypes.get(i);
                break;
            case R.id.spinner_car_washer_address:
                address = addresses.get(i);
                break;
            case R.id.spinner_service_type:
                serviceTye = serviceTypes.get(i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.btn_make_payment)
    public void onBtnMakePaymentClicked() {
        price = carType.getPrice();
        String totalPrice = "Car Price:" + price + "tService charge" + serviceTye.getServiceCharge();
        String add = address.getAddress();
        if (!TextUtils.isEmpty(paymentMethod)) {
            sessionManager.setPaymentDone();
            Random rand = new Random();
            int orderId = rand.nextInt(5);
            Order order =
                    new Order(orderId, orderType, deliveredStationLocation.getCarWasher(), "",
                            deliveredStationLocation.getContact(), add, carType.getType(),
                            paymentMethod, totalPrice + "", serviceTye.getServiceType(),
                            sessionManager.getLatitude(), sessionManager.getLongitude(),
                            MetaData.ORDER_STATUS_LIVE,sessionManager.getFullName());
            order.save();
            onBackPressed();

        } else {
            Toast.makeText(getApplicationContext(), "Please select at least one payment method", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        paymentMethod = paymentMethods.get(position).getPaymentMethod();
        for (int i = 0; i < paymentMethods.size(); i++) {
            paymentMethods.get(i).setSelected(false);
        }
        paymentMethods.get(position).setSelected(true);
        paymentREcyclerView.getAdapter().notifyDataSetChanged();
    }
}
