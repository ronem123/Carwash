package com.ronem.carwash.view;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
//import com.onesignal.OneSignal;
//import com.ronem.carwash.onesignal.OneSignalOpenHandler;

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
