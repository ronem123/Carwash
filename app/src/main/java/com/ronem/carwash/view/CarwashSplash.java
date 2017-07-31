package com.ronem.carwash.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ronem.carwash.R;

public class CarwashSplash extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carwash_splash);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(CarwashSplash.this, LoginRegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        handler.postDelayed(runnable, 2000);
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
