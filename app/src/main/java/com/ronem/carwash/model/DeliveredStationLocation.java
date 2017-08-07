package com.ronem.carwash.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ronem on 8/2/17.
 */

public class DeliveredStationLocation implements Parcelable{
    private int lId;
    private String carWasher;
    private String address;
    private String contact;
    private int rating;
    private double latitude;
    private double longitude;

    public DeliveredStationLocation(int lId, String carWasher, String address, String contact, int rating, double latitude, double longitude) {
        this.lId = lId;
        this.carWasher = carWasher;
        this.address = address;
        this.contact = contact;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected DeliveredStationLocation(Parcel in) {
        lId = in.readInt();
        carWasher = in.readString();
        address = in.readString();
        contact = in.readString();
        rating = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<DeliveredStationLocation> CREATOR = new Creator<DeliveredStationLocation>() {
        @Override
        public DeliveredStationLocation createFromParcel(Parcel in) {
            return new DeliveredStationLocation(in);
        }

        @Override
        public DeliveredStationLocation[] newArray(int size) {
            return new DeliveredStationLocation[size];
        }
    };

    public int getlId() {
        return lId;
    }

    public String getCarWasher() {
        return carWasher;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public int getRating() {
        return rating;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(lId);
        parcel.writeString(carWasher);
        parcel.writeString(address);
        parcel.writeString(contact);
        parcel.writeInt(rating);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}
