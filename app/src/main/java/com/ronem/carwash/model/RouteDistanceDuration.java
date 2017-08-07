package com.ronem.carwash.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ronem on 8/7/17.
 */

public class RouteDistanceDuration {
    private List<List<HashMap<String, String>>> routes;
    private String distance;
    private String duration;

    public RouteDistanceDuration(List<List<HashMap<String, String>>> routes, String distance, String duration) {
        this.routes = routes;
        this.distance = distance;
        this.duration = duration;
    }

    public List<List<HashMap<String, String>>> getRoutes() {
        return routes;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }
}
