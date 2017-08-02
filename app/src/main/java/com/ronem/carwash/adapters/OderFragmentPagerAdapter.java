package com.ronem.carwash.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ronem.carwash.view.order.FragmentLiveOrder;

/**
 * Created by ronem on 8/2/17.
 */

public class OderFragmentPagerAdapter extends FragmentStatePagerAdapter {
    String[] tabTitle = new String[]{"LIVE ORDER", "FINISHED ORDER"};

    public OderFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentLiveOrder();
            case 1:
                return new FragmentLiveOrder();
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

