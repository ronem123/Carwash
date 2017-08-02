package com.ronem.carwash.model;

/**
 * Created by ronem on 8/2/17.
 */

public class CarType {
    private int id;
    private String type;
    private String price;

    public CarType(int id, String type, String price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }
}
