package com.ronem.carwash.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.NavAdapter;
import com.ronem.carwash.model.NavItem;
import com.ronem.carwash.utils.ItemDividerDecoration;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.RecyclerItemClickListener;
import com.ronem.carwash.utils.SessionManager;
import com.ronem.carwash.view.login.LoginRegisterClientActivity;
import com.ronem.carwash.view.login.LoginRegisterCustomerActivity;
import com.ronem.carwash.view.editprofile.EditProfileActivity;
import com.ronem.carwash.view.live_processing_orders.FragmentFinishedOrderClient;
import com.ronem.carwash.view.live_processing_orders.FragmentLiveProcessingOrder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ram on 8/12/17.
 */

public class ClientDashboard extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    @Bind(R.id.map_frame)
    FrameLayout mapFrameLayout;
    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;

    private TextView emailTv;
    private TextView contactTv;

    private NavItem[] items;

    private SessionManager sessionManager;
    private String userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        userType = sessionManager.getUserType();
        mapFrameLayout.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
        settingToolbar();
        setUpNavigationMenu();

        Fragment replaceableFragment = new FragmentLiveProcessingOrder();
        replaceFragment(replaceableFragment);

    }

    private void replaceFragment(Fragment replaceableFragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, replaceableFragment).commit();
    }

    private void settingToolbar() {
        //setting toolbar
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
    }

    private void setUpNavigationMenu() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        emailTv = (TextView) navigationView.findViewById(R.id.user_name);
        contactTv = (TextView) navigationView.findViewById(R.id.user_contact);

        /**
         * expandable items
         */
        RelativeLayout carWasherLayout = (RelativeLayout) navigationView.findViewById(R.id.car_washer_layout);
        final LinearLayout expandableLayout = (LinearLayout) navigationView.findViewById(R.id.expandable_layout);
        carWasherLayout.setVisibility(View.GONE);
        expandableLayout.setVisibility(View.GONE);


        /**
         * setting the navigation items to the navigation view
         */
        items = MetaData.getnavItems(userType);
        RecyclerView navRecyclerView = (RecyclerView) navigationView.findViewById(R.id.nav_recycler_view);
        navRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navRecyclerView.addItemDecoration(new ItemDividerDecoration(this, null));
        navRecyclerView.setHasFixedSize(true);
        navRecyclerView.addItemDecoration(new ItemDividerDecoration(this, null));
        navRecyclerView.setAdapter(new NavAdapter(items));
        navRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

    }

    private void updateUserInfo() {
        String sEmail = sessionManager.getEmail();
        String sContact = sessionManager.getContact();
        emailTv.setText(sEmail);
        contactTv.setText(sContact);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        drawerLayout.closeDrawer(GravityCompat.START);
        NavItem ni = items[position];
        switch (ni.getTitle()) {
            case MetaData.ITEM_LIVE_ORDER:
                replaceFragment(new FragmentLiveProcessingOrder());
                break;
            case MetaData.ITEM_FINISHED_ORDER:
                replaceFragment(new FragmentFinishedOrderClient());
                break;
            case MetaData.ITEM_NOTIFICATIONS:
                Toast.makeText(getApplicationContext(), "Under construction", Toast.LENGTH_SHORT).show();
                break;
            case MetaData.ITEM_EDIT_PROFILE:
                startActivity(new Intent(ClientDashboard.this, EditProfileActivity.class));
                break;
            case MetaData.ITEM_LOG_OUT:
                sessionManager.logOut();
                Intent i = new Intent(ClientDashboard.this, LoginRegisterClientActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
    }
}
