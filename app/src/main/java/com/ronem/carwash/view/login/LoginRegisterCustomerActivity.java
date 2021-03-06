package com.ronem.carwash.view.login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ronem.carwash.R;
import com.ronem.carwash.adapters.CarTypeAdapterRegister;
import com.ronem.carwash.model.CarType;
import com.ronem.carwash.model.UserDb;
import com.ronem.carwash.utils.BasicUtilityMethods;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.SessionManager;
import com.ronem.carwash.view.dashboard.ClientDashboard;
import com.ronem.carwash.view.dashboard.CustomerDashboard;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ram on 7/31/17.
 */

public class LoginRegisterCustomerActivity
        extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    @Bind(R.id.create_account_layout)
    LinearLayout createAccLayout;
    @Bind(R.id.login_layout)
    LinearLayout loginLayout;

    @Bind(R.id.edt_full_name)
    EditText edtFullName;
    @Bind(R.id.edt_email)
    EditText edtEmail;
    @Bind(R.id.edt_password)
    EditText edtPassword;
    @Bind(R.id.edt_confirm_password)
    EditText edtConfirmPassword;
    @Bind(R.id.edt_contact)
    EditText edtContact;
    @Bind(R.id.create_account_spinner_car_type)
    Spinner spinnerCarType;

    @Bind(R.id.radio_group)
    RadioGroup radioGroup;

    @Bind(R.id.edt_login_email)
    EditText edtLoginEmail;
    @Bind(R.id.edt_login_password)
    EditText edtLoginPassword;

    private SessionManager sessionManager;
    private final int PERMISSION_REQUEST_CODE = 100;
    private List<CarType> carTypes;
    private CarType carType;
    private String myLatitude, myLongitude;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private String userType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_register_layout);
        ButterKnife.bind(this);

        radioGroup.setVisibility(View.GONE);

        sessionManager = new SessionManager(this);
        loginLayout.setVisibility(View.VISIBLE);
        createAccLayout.setVisibility(View.GONE);

        configureSCarTypeSpinner();


        createGoogleApiClient();
//        googleApiClient.connect();

        checkRunTimePermissionLaunchDashboard();
        locationRequest = BasicUtilityMethods.createLocationRequest();
    }


    private void createGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void configureSCarTypeSpinner() {
        carTypes = new ArrayList<>();
        carTypes.add(new CarType(0, MetaData.SELECT_CAR_TYPE, ""));
        carTypes.addAll(MetaData.getCarType());

        spinnerCarType.setAdapter(new CarTypeAdapterRegister(carTypes, this));
        spinnerCarType.setOnItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_create_account)
    public void onBtnCreateAccountClicked() {
        String fullName = edtFullName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String contact = edtContact.getText().toString();


        if (TextUtils.isEmpty(fullName)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword)
                || TextUtils.isEmpty(contact)) {
            showMessage(MetaData.MSG_EMPTY_FIELD);
        } else if (carType.getType().equals(MetaData.SELECT_CAR_TYPE)) {
            showMessage(MetaData.MSG_SELECT_CAR_TYPE);
        } else if (!password.equals(confirmPassword)) {
            showMessage(MetaData.MSG_PASSWORD_NOT_MATCHED);
        } else {
            if (BasicUtilityMethods.isGPSEnabled(this)) {
                if (TextUtils.isEmpty(myLatitude) && TextUtils.isEmpty(myLongitude)) {
                    showMessage("Please wait accessing your location");
                } else {
                    List<UserDb> users = UserDb.getUsers();
                    if (users != null && users.size() > 0) {
                        for (UserDb ud : users) {
                            if (ud.userName.equals(email) && ud.userType.equals(MetaData.USER_TYPE_CUSTOMER)) {
                                Toast.makeText(getApplicationContext(), "Sorry ! user already exists with this email", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    sessionManager.setLogin(MetaData.USER_TYPE_CUSTOMER, fullName, email, password, contact, carType.getId(), myLatitude, myLongitude);
                    UserDb u = new UserDb();
                    u.userId = getUserID();
                    u.userType = MetaData.USER_TYPE_CUSTOMER;
                    u.userName = email;
                    u.userPassword = password;
                    u.fullName = fullName;
                    u.contact = contact;
                    u.latitude = myLatitude;
                    u.longitude = myLongitude;
                    u.save();

                    launchDashboard();
                }
            } else {
                BasicUtilityMethods.openGPSSettingDialog(this);
            }
        }
    }

    private int getUserID() {

        int counter = sessionManager.getLatestUserCounter();
        counter++;
        sessionManager.setUserCounter(counter);

        return counter;
    }

    private void checkRunTimePermissionLaunchDashboard() {
        if (Build.VERSION.SDK_INT >= 23
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel("You need to allow access to location",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{
                                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
                                }, PERMISSION_REQUEST_CODE);
                            }
                        });
            }

            requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_REQUEST_CODE);

        } else {
            BasicUtilityMethods.checkifGPSisEnabled(this);
            googleApiClient.connect();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {

            //if user has granted the permissions
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i("Permission", "received");
                if (!googleApiClient.isConnected()) {
                    Log.i("GoogleClient", "was not connected");
                    googleApiClient.connect();
                }
                if (!BasicUtilityMethods.isGPSEnabled(this)) {
                    BasicUtilityMethods.openGPSSettingDialog(this);
                }
            } else {
                checkRunTimePermissionLaunchDashboard();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
        String email = edtLoginEmail.getText().toString();
        String password = edtLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)) {
            showMessage(MetaData.MSG_EMPTY_FIELD);
        } else {

            for (UserDb ud : UserDb.getUsers()) {
                Log.i("Users:", ud.userType + " : " + ud.userName + " : " + ud.userPassword);

                if (ud.userType.equals(MetaData.USER_TYPE_CUSTOMER)) {
                    if (ud.userName.equals(email) && ud.userPassword.equals(password)) {
                        sessionManager.setLogin(ud.userType, ud.fullName, ud.userName, ud.userPassword, ud.contact, ud.carType, ud.latitude, ud.longitude);
                        launchDashboard();
                        return;
                    }
                }
            }
            Toast.makeText(getApplicationContext(), "Sorry user not exists", Toast.LENGTH_SHORT).show();

        }
    }

    @OnClick(R.id.btn_new_to_carwash_wash)
    public void onBtnNewToCarwashClicked() {
        createAccLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_already_member)
    public void onBtnAlreadyMemberClicked() {
        createAccLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
    }

    private void launchDashboard() {
        Intent i = new Intent(this, CustomerDashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        carType = carTypes.get(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        myLatitude = String.valueOf(location.getLatitude());
        myLongitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("GoogleClient", "Connected");
        requestLocationupdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("GoogleClient", "Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("GoogleClient", "Connection Failed");
    }

    @SuppressWarnings("MissingPermission")
    private void requestLocationupdate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        checkRunTimePermissionLaunchDashboard();
    }
}
