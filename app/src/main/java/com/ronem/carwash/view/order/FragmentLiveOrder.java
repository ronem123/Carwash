package com.ronem.carwash.view.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;

import butterknife.ButterKnife;

/**
 * Created by ronem on 8/2/17.
 */

public class FragmentLiveOrder extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_live_order, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}
