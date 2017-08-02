package com.ronem.carwash.model;

/**
 * Created by ronem on 8/2/17.
 */

public class CarWashLocation {
    private int lId;
    private String title;
    private String address;
    private double latitude;
    private double longitude;

    public CarWashLocation(int lId, String title, String address, double latitude, double longitude) {
        this.lId = lId;
        this.title = title;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getlId() {
        return lId;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
