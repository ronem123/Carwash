package com.ronem.carwash.view.live_processing_orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.OrderFragmentPagerAdapterClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ram on 8/12/17.
 */

public class FragmentLiveProcessingOrder extends Fragment {

    @Bind(R.id.tab_layout)
    TabLayout adTabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.order_layout_live_processing_client, container, false);
        ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpViewPager();

    }


    private void setUpViewPager() {
        viewPager.setAdapter(new OrderFragmentPagerAdapterClient(getChildFragmentManager()));
        adTabLayout.setupWithViewPager(viewPager);
        adTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
