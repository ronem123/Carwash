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
import com.google.android.gms.maps.model.PolylineOptions;
import com.ronem.carwash.R;
import com.ronem.carwash.model.DeliveredStationLocation;
import com.ronem.carwash.utils.BasicUtilityMethods;
import com.ronem.carwash.utils.DistanceCalculator;
import com.ronem.carwash.utils.MetaData;
import com.ronem.carwash.utils.SessionManager;
import com.ronem.carwash.view.MyDialog;
import com.ronem.carwash.view.dashboard.Dashboard;
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
    private List<DeliveredStationLocation> stationLocations;
    private DirectionPresenter presenter;
    private Marker mPositionMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivered_layout);

        ButterKnife.bind(this);
        settingToolbar();

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
    public void onLocationChanged(Location location) {
        if (location == null)
            return;

        if (mPositionMarker == null) {

            MarkerOptions markerOption = new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.mipmap.ic_person_black_24dp)))
                    .position(new LatLng(location.getLatitude(), location
                            .getLongitude()));

            mPositionMarker = googleMap.addMarker(markerOption);
            mPositionMarker.setTag(-1);
        }

        animateMarker(mPositionMarker, location); // Helper method for smooth
        // animation

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location
                .getLatitude(), location.getLongitude())));


        double shortDistance = 0;
        DeliveredStationLocation nearLocation = null;
        LatLng myLatLang = new LatLng(location.getLatitude(), location.getLongitude());
        LatLng destinationLatLang;

        for (int i = 0; i < stationLocations.size(); i++) {
            DeliveredStationLocation c = stationLocations.get(i);

            double actualDistance = DistanceCalculator.getDistance(location.getLatitude(), location.getLongitude(),
                    c.getLatitude(), c.getLongitude(), "K");

            if (i == 0) {
                shortDistance = actualDistance;
                nearLocation = c;
            } else {
                if (actualDistance < shortDistance) {
                    shortDistance = actualDistance;
                    nearLocation = c;
                }
            }
            Log.i("Distance", String.valueOf(actualDistance));
        }
        Log.i("ShortestDistance", String.valueOf(shortDistance));

        destinationLatLang = new LatLng(nearLocation.getLatitude(), nearLocation.getLongitude());
        //url for the data between origin and destination
        String url = BasicUtilityMethods.getUrl(myLatLang, destinationLatLang);

        if (BasicUtilityMethods.isNetworkOnline(this)) {
            presenter.onGetPolyLineOptions(url);
        } else {
            Toast.makeText(getApplicationContext(), "Please enable your internet connection", Toast.LENGTH_SHORT).show();
        }

        //showing the destination marker
        MarkerOptions markerOption = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(destinationLatLang);

        Marker m = googleMap.addMarker(markerOption);
        m.setTag(nearLocation.getlId());
        Log.i("location:", nearLocation.getLatitude() + "\n" + nearLocation.getLongitude());
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
                if (tag != -1) {
                    MyDialog d = new MyDialog(DeliveredActivity.this, 1);
                    d.show();
                }
                return true;
            }
        });
    }

    @Override
    public void onPolyLineOptionReceived(PolylineOptions polylineOptions) {
        googleMap.addPolyline(polylineOptions);
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
