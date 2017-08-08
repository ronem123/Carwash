package com.ronem.carwash.view.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ronem.carwash.R;
import com.ronem.carwash.adapters.NavAdapter;
import com.ronem.carwash.model.DeliveredStationLocation;
import com.ronem.carwash.model.NavItem;
import com.ronem.carwash.utils.BasicUtilityMethods;
import com.ronem.carwash.utils.DistanceCalculator;
import com.ronem.carwash.utils.ItemDividerDecoration;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.RecyclerItemClickListener;
import com.ronem.carwash.utils.SessionManager;
import com.ronem.carwash.view.LoginRegisterActivity;
import com.ronem.carwash.view.MyDialog;
import com.ronem.carwash.view.delivered.DeliveredActivity;
import com.ronem.carwash.view.editprofile.EditProfileActivity;
import com.ronem.carwash.view.order.OrderActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ram on 8/1/17.
 */

public class Dashboard extends AppCompatActivity
        implements RecyclerItemClickListener.OnItemClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        DirectionAdView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private TextView emailTv;
    private TextView contactTv;

    private NavItem[] items;


    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private GoogleMap googleMap;
    private List<DeliveredStationLocation> stationLocations;
    private DirectionPresenter presenter;
    private Marker mPositionMarker;
    private Location myLocation;
    private LatLng myLatlang, destinationLatlang;
    private DeliveredStationLocation stationLocation;
    //    private List<Marker> markers;
    private PolylineOptions polylineOptions;
    private Polyline polyline;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        settingToolbar();
        setUpNavigationMenu();

        presenter = new DirectionPresenterImpl();
        presenter.onAddDirectionView(this);
        BasicUtilityMethods.checkifGPSisEnabled(this);

        stationLocations = MetaData.getStations();

        createGoogleApiClient();
        googleApiClient.connect();

        locationRequest = BasicUtilityMethods.createLocationRequest();

        loadmap();
    }


    private void createGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void loadmap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        emailTv = (TextView) navigationView.findViewById(R.id.user_name);
        contactTv = (TextView) navigationView.findViewById(R.id.user_contact);

        /**
         * expandable items
         */
        RelativeLayout carWasherLayout = (RelativeLayout) navigationView.findViewById(R.id.car_washer_layout);
        final LinearLayout expandableLayout = (LinearLayout) navigationView.findViewById(R.id.expandable_layout);
        LinearLayout deliveredLayout = (LinearLayout) navigationView.findViewById(R.id.delivered_layout);
        LinearLayout stationLayout = (LinearLayout) navigationView.findViewById(R.id.station_layout);


        View.OnClickListener myNavItemListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.car_washer_layout:
                        if (expandableLayout.getVisibility() == View.VISIBLE) {
                            expandableLayout.setVisibility(View.GONE);
                        } else {
                            expandableLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.delivered_layout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(Dashboard.this, DeliveredActivity.class));
                        break;
                    case R.id.station_layout:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        };

        carWasherLayout.setOnClickListener(myNavItemListener);
        deliveredLayout.setOnClickListener(myNavItemListener);
        stationLayout.setOnClickListener(myNavItemListener);


        /**
         * setting the navigation items to the navigation view
         */
        items = MetaData.getnavItems();
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
        switch (position) {
            case 0:
                startActivity(new Intent(Dashboard.this, OrderActivity.class));
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Under construction", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                startActivity(new Intent(Dashboard.this, EditProfileActivity.class));
                break;
            case 3:
                sessionManager.logOut();
                Intent i = new Intent(Dashboard.this, LoginRegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationupdate();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location l) {
        if (l == null)
            return;

        if (mPositionMarker == null) {

            myLocation = l;
            myLatlang = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

            MarkerOptions markerOption = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .position(myLatlang);

            mPositionMarker = googleMap.addMarker(markerOption);
            mPositionMarker.setTag(-1);
        }

        animateMarker(mPositionMarker, myLocation); // Helper method for smooth
        // animation

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(myLatlang));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        googleMap.setMyLocationEnabled(false);
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Integer tag = (Integer) marker.getTag();
                destinationLatlang = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);

                if (tag != -1) {

                    if (myLocation == null) {
                        Toast.makeText(getApplicationContext(), "sorry location not found", Toast.LENGTH_SHORT).show();
                    } else {
                        String url = BasicUtilityMethods.getUrl(myLatlang, destinationLatlang);
                        Log.i("URL::", url);
                        if (BasicUtilityMethods.isNetworkOnline(Dashboard.this)) {
                            for (DeliveredStationLocation d : stationLocations) {
                                if (d.getlId() == tag) {
                                    stationLocation = d;
                                }
                            }
                            presenter.onGetPolyLineOptions(url);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enable your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return true;
            }
        });
        for (int i = 0; i < stationLocations.size(); i++) {
            DeliveredStationLocation d = stationLocations.get(i);
            LatLng l = new LatLng(d.getLatitude(), d.getLongitude());
            MarkerOptions markerOption = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(l);
            Marker m = googleMap.addMarker(markerOption);
            m.setTag(d.getlId());

//            markers.add(m);
        }

    }

    @SuppressWarnings("MissingPermission")
    private void requestLocationupdate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserInfo();
        if (googleApiClient.isConnected()) {
            requestLocationupdate();
        }

        if (polylineOptions != null) {
            if (sessionManager.getPaymentDone()) {
                if (polyline != null) {
                    polyline.remove();
                }
                polyline = googleMap.addPolyline(polylineOptions);
                sessionManager.clearPaymentDone();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @Override
    public void onPolyLineOptionReceived(PolylineOptions polylineOptions,String distance,String duration) {
        MyDialog d = new MyDialog(Dashboard.this, distance, duration, stationLocation, MetaData.ORDER_TYPE_STATION);
        d.show();
        this.polylineOptions = polylineOptions;
    }

    private void animateMarker(final Marker marker, final Location location) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final LatLng startLatLng = marker.getPosition();
        final double startRotation = marker.getRotation();
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);

                double lng = t * location.getLongitude() + (1 - t)
                        * startLatLng.longitude;
                double lat = t * location.getLatitude() + (1 - t)
                        * startLatLng.latitude;

                float rotation = (float) (t * location.getBearing() + (1 - t)
                        * startRotation);

                marker.setPosition(new LatLng(lat, lng));
                marker.setRotation(rotation);

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {

        View customMarkerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.marker_layout, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.marker_icon);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
