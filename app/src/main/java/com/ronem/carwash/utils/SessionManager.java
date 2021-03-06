package com.ronem.carwash.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Created by ram on 8/1/17.
 */

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String IS_LOGIN = "islogin";
    private final String KEY_USER_TYPE = "user_type";
    private final String KEY_FULL_NAME = "full_name";
    private final String KEY_EMAIL = "email";
    private final String KEY_PASSWORD = "password";
    private final String KEY_CONTACT = "contact";
    private final String KEY_CAR_TYPE = "car_type";
    private final String KEY_LATI = "latitude";
    private final String KEY_LONGI = "longi";

    private final String KEY_COUNTER = "counter";
    private final String KEY_USER_COUNTER = "user_counter";

    private final String KEY_PAYMENT_DONE = "payment_done";

    public SessionManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogin(String userType, String fullname, String email, String password, String contact, int carType, String lat, String longi) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_FULL_NAME, fullname);
        editor.putString(KEY_EMAIL, email);
        if (!TextUtils.isEmpty(password)) {
            editor.putString(KEY_PASSWORD, password);
        }
        editor.putString(KEY_USER_TYPE, userType);
        editor.putString(KEY_CONTACT, contact);
        editor.putInt(KEY_CAR_TYPE, carType);
        editor.putString(KEY_LATI, lat);
        editor.putString(KEY_LONGI, longi);
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();
    }

    public String getFullName() {
        return sharedPreferences.getString(KEY_FULL_NAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public String getContact() {
        return sharedPreferences.getString(KEY_CONTACT, "");
    }

    public int getCarType() {
        return sharedPreferences.getInt(KEY_CAR_TYPE, 0);
    }

    public String getUserType() {
        return sharedPreferences.getString(KEY_USER_TYPE, "");
    }

    public String getLatitude() {
        return sharedPreferences.getString(KEY_LATI, "0.0");

    }

    public String getLongitude() {
        return sharedPreferences.getString(KEY_LONGI, "0.0");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logOut() {
        editor = sharedPreferences.edit();
        editor.putString(KEY_FULL_NAME, "");
        editor.putString(KEY_EMAIL, "");
        editor.putString(KEY_PASSWORD, "");
        editor.putString(KEY_CONTACT, "");
        editor.putBoolean(IS_LOGIN, false);
        editor.apply();
    }

    public void setPaymentDone() {
        editor = sharedPreferences.edit();
        editor.putBoolean(KEY_PAYMENT_DONE, true).apply();
    }

    public boolean getPaymentDone() {
        return sharedPreferences.getBoolean(KEY_PAYMENT_DONE, false);
    }

    public void clearPaymentDone() {
        editor = sharedPreferences.edit();
        editor.putBoolean(KEY_PAYMENT_DONE, false).apply();
    }


    //update the counter of order
    public void setOrderCounter(int counter) {
        editor = sharedPreferences.edit();
        editor.putInt(KEY_COUNTER, counter).apply();
    }

    public int getLatestCounter() {
        return sharedPreferences.getInt(KEY_COUNTER, 0);
    }

    //update the counter of users
    public void setUserCounter(int counter) {
        editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_COUNTER, counter).apply();
    }

    public int getLatestUserCounter() {
        return sharedPreferences.getInt(KEY_USER_COUNTER, 0);
    }
}
