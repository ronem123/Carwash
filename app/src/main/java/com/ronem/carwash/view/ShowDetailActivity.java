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
import android.widget.Spinner;
import android.widget.Toast;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.AddressAdapter;
import com.ronem.carwash.adapters.CarTypeAdapter;
import com.ronem.carwash.adapters.PaymentAdapter;
import com.ronem.carwash.model.Address;
import com.ronem.carwash.model.CarType;
import com.ronem.carwash.model.PaymentMethod;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.RecyclerItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 8/2/17.
 */

public class ShowDetailActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, RecyclerItemClickListener.OnItemClickListener {
    @Bind(R.id.spinner_car_type)
    Spinner spinnerCarType;
    @Bind(R.id.spinner_car_washer_address)
    Spinner spinnerAddress;
    @Bind(R.id.payment_recycler_view)
    RecyclerView paymentREcyclerView;


    private List<CarType> carTypes;
    private List<Address> addresses;

    private CarType carType;
    private Address address;
    private String price;
    private String paymentMethod;
    private List<PaymentMethod> paymentMethods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_detail_activity);
        ButterKnife.bind(this);

        carTypes = MetaData.getCarType();
        addresses = MetaData.getAddress();
        paymentMethods = MetaData.getpaymentMEthods();

        spinnerCarType.setAdapter(new CarTypeAdapter(carTypes, this));
        spinnerAddress.setAdapter(new AddressAdapter(addresses, this));

        spinnerCarType.setOnItemSelectedListener(this);
        spinnerAddress.setOnItemSelectedListener(this);

        paymentREcyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentREcyclerView.setHasFixedSize(true);
        paymentREcyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
        paymentREcyclerView.setAdapter(new PaymentAdapter(paymentMethods));

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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.btn_make_payment)
    public void onBtnMakePaymentClicked() {
        price = carType.getPrice();
        String type = carType.getType();
        String add = address.getAddress();
        if (!TextUtils.isEmpty(paymentMethod)) {

            Log.i("sum", type + "\n" + add + "\n" + price + "\n" + paymentMethod);
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
