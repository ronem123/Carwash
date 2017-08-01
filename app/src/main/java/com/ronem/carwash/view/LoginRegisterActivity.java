package com.ronem.carwash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ronem.carwash.R;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ram on 7/31/17.
 */

public class LoginRegisterActivity extends AppCompatActivity {
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

    @Bind(R.id.edt_login_email)
    EditText edtLoginEmail;
    @Bind(R.id.edt_login_password)
    EditText edtLoginPassword;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_register_layout);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(this);
//        if (sessionManager.isLoggedIn()) {
//            launchDashboard();
//        } else {
            loginLayout.setVisibility(View.VISIBLE);
            createAccLayout.setVisibility(View.GONE);
//        }
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
        } else if (!password.equals(confirmPassword)) {
            showMessage(MetaData.MSG_PASSWORD_NOT_MATCHED);
        } else {
            sessionManager.setLogin(fullName, email, password, contact);
            launchDashboard();
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
            String sEmail = sessionManager.getEmail();
            String sPassword = sessionManager.getPassword();

            if (!email.equals(sEmail)) {
                edtLoginEmail.setError(MetaData.MSG_EMAIL_NOT_FOUND);
            } else if (!password.equals(sPassword)) {
                edtLoginPassword.setError(MetaData.MSG_PASSWORD_NOT_MATCHED);
            } else {
                launchDashboard();
            }
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
        Intent i = new Intent(this, Dashboard.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void showMessage(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
