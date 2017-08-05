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
    private final String KEY_FULL_NAME = "full_name";
    private final String KEY_EMAIL = "email";
    private final String KEY_PASSWORD = "password";
    private final String KEY_CONTACT = "contact";
    private final String KEY_CAR_TYPE = "car_type";

    public SessionManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogin(String fullname, String email, String password, String contact, int carType) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_FULL_NAME, fullname);
        editor.putString(KEY_EMAIL, email);
        if (!TextUtils.isEmpty(password)) {
            editor.putString(KEY_PASSWORD, password);
        }
        editor.putString(KEY_CONTACT, contact);
        editor.putInt(KEY_CAR_TYPE, carType);
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
}
