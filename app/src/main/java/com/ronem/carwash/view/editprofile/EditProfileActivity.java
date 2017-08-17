package com.ronem.carwash.view.editprofile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.CarTypeAdapterRegister;
import com.ronem.carwash.model.CarType;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ram on 8/5/17.
 */

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.edt_full_name)
    EditText edtFullName;
    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_contact)
    EditText edtContact;
    @Bind(R.id.create_account_spinner_car_type)
    Spinner spinnerCarType;
    @Bind(R.id.car_type_layout)
    LinearLayout carTypeLayout;

    private SessionManager sessionManager;
    private List<CarType> carTypes;
    private CarType carType;
    private String userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_layout);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);
        edtFullName.setText(sessionManager.getFullName());
        edtEmail.setText(sessionManager.getEmail());
        edtContact.setText(sessionManager.getContact());

        configureSCarTypeSpinner();
        userType = sessionManager.getUserType();

        if (!userType.equals(MetaData.USER_TYPE_CUSTOMER)) {
            carTypeLayout.setVisibility(View.GONE);
        }
    }

    private void configureSCarTypeSpinner() {
        carTypes = new ArrayList<>();
        carTypes.add(new CarType(0, MetaData.SELECT_CAR_TYPE, ""));
        carTypes.addAll(MetaData.getCarType());

        spinnerCarType.setAdapter(new CarTypeAdapterRegister(carTypes, this));
        spinnerCarType.setOnItemSelectedListener(this);
        spinnerCarType.setSelection(sessionManager.getCarType());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        carType = carTypes.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.btn_update_profile)
    public void onBtnUpdateProfileClicked() {
        String fullName = edtFullName.getText().toString();
        String email = edtEmail.getText().toString();
        String contact = edtContact.getText().toString();

        if (TextUtils.isEmpty(fullName)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(contact)) {
            showMessage(MetaData.MSG_EMPTY_FIELD);
        } else {
            if (userType.equals(MetaData.USER_TYPE_CUSTOMER)) {
                if (carType.getType().equals(MetaData.SELECT_CAR_TYPE)) {
                    showMessage(MetaData.MSG_SELECT_CAR_TYPE);
                } else {
                    sessionManager.setLogin(userType, fullName, email, sessionManager.getPassword(), contact, carType.getId(), sessionManager.getLatitude(), sessionManager.getLongitude());
                    Toast.makeText(getApplicationContext(), "profile updated", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            } else {
                sessionManager.setLogin(userType, fullName, email, sessionManager.getPassword(), contact, sessionManager.getCarType(), sessionManager.getLatitude(), sessionManager.getLongitude());
                Toast.makeText(getApplicationContext(), "profile updated", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }

            onBackPressed();
        }
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
