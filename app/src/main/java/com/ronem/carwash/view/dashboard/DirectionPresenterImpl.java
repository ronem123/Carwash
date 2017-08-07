package com.ronem.carwash.view.dashboard;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ronem.carwash.model.RouteDistanceDuration;
import com.ronem.carwash.utils.BasicUtilityMethods;
import com.ronem.carwash.utils.DataParser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ram on 7/10/17.
 */

public class DirectionPresenterImpl implements DirectionPresenter {
    private DirectionAdView directionAdView;

    @Override
    public void onAddDirectionView(DirectionAdView directionAdView) {
        this.directionAdView = directionAdView;
    }

    @Override
    public void onGetPolyLineOptions(String url) {
        FetchUrl FetchUrl = new FetchUrl();
        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);
    }


    private class ParserTask extends AsyncTask<String, Integer, RouteDistanceDuration> {

        // Parsing the data in non-ui thread
        @Override
        protected RouteDistanceDuration doInBackground(String... jsonData) {

            JSONObject jObject;
            RouteDistanceDuration rdd = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DataParser parser = new DataParser();

                // Starts parsing data
                rdd = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return rdd;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(RouteDistanceDuration result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.getRoutes().size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.getRoutes().get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.GREEN);


            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
//                googleMap.addPolyline(lineOptions);
                directionAdView.onPolyLineOptionReceived(lineOptions, result.getDistance(), result.getDuration());
            } else {
                Log.i("onPostExecute", "without Polylines drawn");
            }
        }
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = BasicUtilityMethods.downloadUrl(url[0]);
            } catch (Exception e) {
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (!TextUtils.isEmpty(result)) {
                ParserTask parserTask = new ParserTask();
                // Invokes the thread for parsing the JSON data
                parserTask.execute(result);
            }

        }
    }
}
