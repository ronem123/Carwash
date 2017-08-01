package com.ronem.carwash.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ronem.carwash.R;
import com.ronem.carwash.adapters.NavAdapter;
import com.ronem.carwash.model.NavItem;
import com.ronem.carwash.utils.ItemDividerDecoration;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.RecyclerItemClickListener;
import com.ronem.carwash.utils.SessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ram on 8/1/17.
 */

public class Dashboard extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private NavItem[] items;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        settingToolbar();
        setUpNavigationMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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

        /**
         * setting user info to the navigation header layout when login successful
         */
        String sEmail = sessionManager.getEmail();
        String sContact = sessionManager.getContact();
        TextView emailTV = (TextView) navigationView.findViewById(R.id.user_name);
        TextView contactTv = (TextView) navigationView.findViewById(R.id.user_contact);
        emailTV.setText(sEmail);
        contactTv.setText(sContact);

        /**
         * setting the navigation items to the navigation view
         */
        items = MetaData.getnavItems();
        RecyclerView navRecyclerView = (RecyclerView) navigationView.findViewById(R.id.nav_recycler_view);
        navRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navRecyclerView.addItemDecoration(new ItemDividerDecoration(this, null));
        navRecyclerView.setHasFixedSize(true);
        navRecyclerView.setAdapter(new NavAdapter(items));
        navRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {
        Toast.makeText(getApplicationContext(), items[position].getTitle(), Toast.LENGTH_SHORT).show();
    }
}
