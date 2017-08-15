package com.ronem.carwash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ronem.carwash.R;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.view.login.LoginRegisterClientActivity;
import com.ronem.carwash.view.login.LoginRegisterCustomerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ram on 8/15/17.
 */

public class UserTypeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_type_selection_layout);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_user_type_customer)
    public void onBtnUserTypeCustomerClicked() {
        Intent i = new Intent(this, LoginRegisterCustomerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @OnClick(R.id.btn_user_type_client)
    public void onBtnUserTypeClientClicked() {
        Intent i = new Intent(this, LoginRegisterClientActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}
