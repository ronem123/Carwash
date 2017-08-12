package com.ronem.carwash.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ronem.carwash.view.live_processing_orders.FragmentLiveOrderClient;
import com.ronem.carwash.view.live_processing_orders.FragmentProcessingOrderClient;

/**
 * Created by ram on 8/12/17.
 */

public class OrderFragmentPagerAdapterClient extends FragmentStatePagerAdapter {
    String[] tabTitle = new String[]{"LIVE ORDER", "PROCESSING ORDER"};

    public OrderFragmentPagerAdapterClient(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentLiveOrderClient();
            case 1:
                return new FragmentProcessingOrderClient();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}


