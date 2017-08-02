package com.ronem.carwash.view.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.OderFragmentPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ronem on 8/2/17.
 */

public class OrderActivity extends AppCompatActivity {


    @Bind(R.id.tab_layout)
    TabLayout adTabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        TextView toolbarTv = (TextView)findViewById(R.id.toolbar_title_tv);

        ButterKnife.bind(this);

        //set up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTv.setText("Orders");

        //setup viewpager with tab
        setUpViewPager();

    }

    private void setUpViewPager() {
        viewPager.setAdapter(new OderFragmentPagerAdapter(getSupportFragmentManager()));
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
