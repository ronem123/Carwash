package com.ronem.carwash.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ronem.carwash.R;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.SessionManager;
import com.ronem.carwash.view.dashboard.ClientDashboard;
import com.ronem.carwash.view.dashboard.CustomerDashboard;

public class CarwashSplash extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carwash_splash);

        handler = new Handler();
        sessionManager = new SessionManager(this);

        runnable = new Runnable() {
            @Override
            public void run() {

                if (sessionManager.isLoggedIn()) {
                    launchDashboard();
                } else {
                    Intent i = new Intent(CarwashSplash.this, UserTypeSelectionActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }

            }
        };

        handler.postDelayed(runnable, 2000);
    }

    private void launchDashboard() {
        Intent i;
        if (sessionManager.getUserType().equals(MetaData.USER_TYPE_CUSTOMER)) {
            i = new Intent(this, CustomerDashboard.class);
        } else {
            i = new Intent(this, ClientDashboard.class);
        }

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }
}
