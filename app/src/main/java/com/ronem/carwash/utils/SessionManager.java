package com.ronem.carwash.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ram on 8/1/17.
 */

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String IS_LOGIN = "islogin";
    private final String KEY_FULL_NAME = "full_name";
    private final String KEY_EMAIL = "email";
    private final String KEY_CONTACT = "contact";

    public SessionManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private void setLogin(String fullname, String email, String contact) {
        editor = sharedPreferences.edit();
        editor.putString(KEY_FULL_NAME, fullname);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_CONTACT, contact);
        editor.putBoolean(IS_LOGIN, true);
        editor.apply();
    }

    public String getFullName() {
        return sharedPreferences.getString(KEY_FULL_NAME, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getContact() {
        return sharedPreferences.getString(KEY_CONTACT, "");
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
