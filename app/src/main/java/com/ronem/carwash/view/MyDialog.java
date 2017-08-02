package com.ronem.carwash.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.ronem.carwash.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 8/2/17.
 */

public class MyDialog extends Dialog {

    public MyDialog(Context context, int id) {
        super(context, R.style.slideAnimation);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.my_dialog);
        ButterKnife.bind(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.dialog_btn_show_more)
    public void onDialogBtnShowMoreClicked() {
        Intent i = new Intent(getContext(), ShowDetailActivity.class);
        getContext().startActivity(i);
    }

    @OnClick(R.id.dialog_btn_close)
    public void onDialogBtnCloseClicked() {
        dismiss();
    }
}
