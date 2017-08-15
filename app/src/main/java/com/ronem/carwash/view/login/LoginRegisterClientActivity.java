package com.ronem.carwash.view.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ronem.carwash.R;
import com.ronem.carwash.model.UserDb;
import com.ronem.carwash.utils.BasicUtilityMethods;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.SessionManager;
import com.ronem.carwash.view.dashboard.ClientDashboard;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ram on 8/15/17.
 */

public class LoginRegisterClientActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
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
    @Bind(R.id.edt_latitude)
    EditText edtlatitude;
    @Bind(R.id.edt_longitude)
    EditText edtLongitude;
    @Bind(R.id.car_type_layout)
    LinearLayout carTypeLayout;

    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.radio_customer)
    RadioButton radioStation;
    @Bind(R.id.radio_client)
    RadioButton radioSalesMan;
    @Bind(R.id.lat_long_layout)
    LinearLayout latLngLayout;

    //client
    @Bind(R.id.radio_group_login)
    RadioGroup radioGroupLogin;
    @Bind(R.id.radio_btn_station_login)
    RadioButton radioBtnStationLogin;
    @Bind(R.id.radio_btn_sales_man_login)
    RadioButton radioBtnSalesManLogin;

    @Bind(R.id.edt_login_email)
    EditText edtLoginEmail;
    @Bind(R.id.edt_login_password)
    EditText edtLoginPassword;

    private SessionManager sessionManager;
    private final int PERMISSION_REQUEST_CODE = 100;
    private String salesLati, salesLongi;

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

        sessionManager = new SessionManager(this);

        loginLayout.setVisibility(View.VISIBLE);
        radioGroupLogin.setVisibility(View.VISIBLE);
        createAccLayout.setVisibility(View.GONE);
        carTypeLayout.setVisibility(View.GONE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (!BasicUtilityMethods.isGPSEnabled(LoginRegisterClientActivity.this)) {
                    BasicUtilityMethods.openGPSSettingDialog(LoginRegisterClientActivity.this);
                }
            }
        });


        createGoogleApiClient();
        googleApiClient.connect();

        locationRequest = BasicUtilityMethods.createLocationRequest();
    }


    private void createGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
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
//        String latitude = edtlatitude.getText().toString();
//        String longitude = edtLongitude.getText().toString();


        if (TextUtils.isEmpty(fullName)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(confirmPassword)
                || TextUtils.isEmpty(contact)) {
            showMessage(MetaData.MSG_EMPTY_FIELD);
        } else if (!password.equals(confirmPassword)) {
            showMessage(MetaData.MSG_PASSWORD_NOT_MATCHED);
        } else {
            if (radioStation.isChecked()) {
                if (BasicUtilityMethods.isGPSEnabled(this)) {
                    if (TextUtils.isEmpty(salesLati) && TextUtils.isEmpty(salesLongi)) {
                        showMessage("Please wait accessing your location");
                    } else {

                        List<UserDb> users = UserDb.getUsers();
                        if (users != null && users.size() > 0) {
                            for (UserDb ud : users) {
                                if (ud.userName.equals(email) && ud.userType.equals(MetaData.USER_TYPE_CLIENT_STATION)) {
                                    Toast.makeText(getApplicationContext(), "Sorry ! user already exists with this email", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }

                        //large db for users
                        UserDb u = new UserDb();
                        u.userId = getUserID();
                        u.userType = MetaData.USER_TYPE_CLIENT_STATION;
                        u.userName = email;
                        u.userPassword = password;
                        u.fullName = fullName;
                        u.contact = contact;
                        u.carType = 0;
                        u.latitude = salesLati;
                        u.longitude = salesLongi;
                        u.save();

                        //small cache for the application
                        sessionManager.setLogin(MetaData.USER_TYPE_CLIENT_STATION, fullName, email, password, contact, -1, salesLati, salesLongi);
                        launchDashboard();
                    }
                } else {
                    BasicUtilityMethods.openGPSSettingDialog(this);
                }
            } else if (radioSalesMan.isChecked()) {

                if (BasicUtilityMethods.isGPSEnabled(this)) {
                    if (TextUtils.isEmpty(salesLati) && TextUtils.isEmpty(salesLongi)) {
                        showMessage("Please wait accessing your location");
                    } else {

                        List<UserDb> users = UserDb.getUsers();
                        if (users != null && users.size() > 0) {
                            for (UserDb ud : users) {
                                if (ud.userName.equals(email) && ud.userType.equals(MetaData.USER_TYPE_CLIENT_SALESMAN)) {
                                    Toast.makeText(getApplicationContext(), "Sorry ! user already exists with this email", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }

                        //large db for users
                        UserDb u = new UserDb();
                        u.userId = getUserID();
                        u.userType = MetaData.USER_TYPE_CLIENT_SALESMAN;
                        u.userName = email;
                        u.userPassword = password;
                        u.fullName = fullName;
                        u.carType = 0;
                        u.contact = contact;
                        u.latitude = salesLati;
                        u.longitude = salesLongi;
                        u.save();

                        //small cache for the application
                        sessionManager.setLogin(MetaData.USER_TYPE_CLIENT_SALESMAN, fullName, email, password, contact, -1, salesLati, salesLongi);
                        launchDashboard();
                    }
                } else {
                    BasicUtilityMethods.openGPSSettingDialog(this);
                }

            } else {
                showMessage("Please select either user or salesman");
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
            requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION
            }, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (!BasicUtilityMethods.isGPSEnabled(this)) {
                BasicUtilityMethods.openGPSSettingDialog(this);
            }
        } else {
            checkRunTimePermissionLaunchDashboard();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.btn_login)
    public void onBtnLoginClicked() {
        String email = edtLoginEmail.getText().toString();
        String password = edtLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)) {
            showMessage(MetaData.MSG_EMPTY_FIELD);
        } else {

            //// TODO: 8/15/17
            if (radioBtnStationLogin.isChecked()) {
                for (UserDb ud : UserDb.getUsers()) {

                    Log.i("Users:", ud.userType + " : " + ud.userName + " : " + ud.userPassword);

                    if (ud.userType.equals(MetaData.USER_TYPE_CLIENT_STATION)) {
                        if (ud.userName.equals(email) && ud.userPassword.equals(password)) {
                            sessionManager.setLogin(ud.userType, ud.fullName, ud.userName, ud.userPassword, ud.contact, ud.carType, ud.latitude, ud.longitude);
                            launchDashboard();
                            return;
                        }
                    }
                }
            } else if (radioBtnSalesManLogin.isChecked()) {
                for (UserDb ud : UserDb.getUsers()) {

                    Log.i("Users:", ud.userType + " : " + ud.userName + " : " + ud.userPassword);

                    if (ud.userType.equals(MetaData.USER_TYPE_CLIENT_SALESMAN)) {
                        if (ud.userName.equals(email) && ud.userPassword.equals(password)) {
                            sessionManager.setLogin(ud.userType, ud.fullName, ud.userName, ud.userPassword, ud.contact, ud.carType, ud.latitude, ud.longitude);
                            launchDashboard();
                            return;
                        }
                    }
                }
                Toast.makeText(getApplicationContext(), "Sorry user does not exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "You need to select the user Type", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @OnClick(R.id.btn_new_to_carwash_wash)
    public void onBtnNewToCarwashClicked() {
        createAccLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
        radioGroupLogin.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_already_member)
    public void onBtnAlreadyMemberClicked() {
        createAccLayout.setVisibility(View.GONE);
        radioGroupLogin.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.VISIBLE);
    }

    private void launchDashboard() {
        Intent i = new Intent(this, ClientDashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location == null)
            return;
        salesLati = String.valueOf(location.getLatitude());
        salesLongi = String.valueOf(location.getLongitude());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationupdate();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @SuppressWarnings("MissingPermission")
    private void requestLocationupdate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkRunTimePermissionLaunchDashboard();
    }
}
