package com.ronem.carwash.model;

/**
 * Created by ronem on 8/2/17.
 */

public class Address {
    private int adId;
    private String address;

    public Address(int adId, String address) {
        this.adId = adId;
        this.address = address;
    }

    public int getAdId() {
        return adId;
    }

    public String getAddress() {
        return address;
    }
}
