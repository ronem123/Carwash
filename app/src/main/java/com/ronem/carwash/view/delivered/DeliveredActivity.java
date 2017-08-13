package com.ronem.carwash.view.delivered;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
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
import com.ronem.carwash.model.DeliveredStationLocation;
import com.ronem.carwash.utils.BasicUtilityMethods;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.SessionManager;
import com.ronem.carwash.view.MyDialog;
import com.ronem.carwash.view.dashboard.DirectionAdView;
import com.ronem.carwash.view.dashboard.DirectionPresenter;
import com.ronem.carwash.view.dashboard.DirectionPresenterImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ronem on 8/7/17.
 */

public class DeliveredActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        DirectionAdView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private GoogleMap googleMap;
    private List<DeliveredStationLocation> deliveredLocations;
    private DirectionPresenter presenter;
    private Marker mPositionMarker;
    private Location myLocation;
    private LatLng myLatlang, destinationLatlang;
    private DeliveredStationLocation deliveredStationLocation;
    //    private List<Marker> markers;
    private PolylineOptions polylineOptions;
    private Polyline polyline;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivered_layout);

        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        settingToolbar();

        presenter = new DirectionPresenterImpl();
        presenter.onAddDirectionView(this);
        BasicUtilityMethods.checkifGPSisEnabled(this);

        deliveredLocations = MetaData.getDeliveredLocations();
//        markers = new ArrayList<>();

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
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(MetaData.MAP_ZOOM));
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
                        if (BasicUtilityMethods.isNetworkOnline(DeliveredActivity.this)) {
                            for (DeliveredStationLocation d : deliveredLocations) {
                                if (d.getlId() == tag) {
                                    deliveredStationLocation = d;
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

        //sprinkle markers

        for (int i = 0; i < deliveredLocations.size(); i++) {
            DeliveredStationLocation d = deliveredLocations.get(i);
            LatLng l = new LatLng(d.getLatitude(), d.getLongitude());
            MarkerOptions markerOption = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position(l);
            Marker m = googleMap.addMarker(markerOption);
            m.setTag(d.getlId());

//            markers.add(m);
        }

        //Draw your circle
//        Circle circle = mMap.addCircle(new CircleOptions()
//                .center(latLng)
//                .radius(400)
//                .strokeColor(Color.rgb(0, 136, 255))
//                .fillColor(Color.argb(20, 0, 136, 255)));
//
//        for (Marker marker : markers) {
//            if (SphericalUtil.computeDistanceBetween(latLng, marker.getPosition()) < 400) {
//                marker.setVisible(true);
//            }
//        }


    }

    @Override
    public void onPolyLineOptionReceived(PolylineOptions polylineOptions, String distance, String duration) {
        MyDialog d = new MyDialog(DeliveredActivity.this, distance, duration, deliveredStationLocation, MetaData.ORDER_TYPE_DELIVERD);
        d.show();
        this.polylineOptions = polylineOptions;
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @SuppressWarnings("MissingPermission")
    private void requestLocationupdate() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected())
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
