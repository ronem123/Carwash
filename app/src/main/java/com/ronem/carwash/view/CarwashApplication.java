package com.ronem.carwash.view;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by ronem on 8/8/17.
 */

public class CarwashApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
