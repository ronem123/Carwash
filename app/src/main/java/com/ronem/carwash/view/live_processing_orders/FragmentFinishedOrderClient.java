package com.ronem.carwash.view.live_processing_orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;

/**
 * Created by ram on 8/12/17.
 */

public class FragmentFinishedOrderClient extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_live_order, container, false);
        return root;
    }
}
