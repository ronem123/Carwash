package com.ronem.carwash.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ronem on 8/8/17.
 */

@Table(name = "order_d_s")
public class Order extends Model {
    @Column(name = "order_id")
    public int orderId;
    @Column(name = "order_type")
    public String orderType;
    @Column(name = "carwasher_name")
    public String carwarsherName;
    @Column(name = "carwasher_email")
    public String carwasherEmail;
    @Column(name = "carwasher_contact")
    public String carWasherContact;
    @Column(name = "carwasher_address")
    public String myAddress;
    @Column(name = "car_type")
    public String carType;
    @Column(name = "payment_method")
    public String paymentMethod;
    @Column(name = "payed_amount")
    public String payedAmount;
    @Column(name = "service")
    public String service;

    public Order(){super();}
    public Order(int orderId, String orderType, String carwarsherName, String carwasherEmail, String carWasherContact, String myAddress,String carType, String paymentMethod, String payedAmount, String service) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.carwarsherName = carwarsherName;
        this.carwasherEmail = carwasherEmail;
        this.carWasherContact = carWasherContact;
        this.myAddress = myAddress;
        this.carType = carType;
        this.paymentMethod = paymentMethod;
        this.payedAmount = payedAmount;
        this.service = service;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getCarwarsherName() {
        return carwarsherName;
    }

    public String getCarwasherEmail() {
        return carwasherEmail;
    }

    public String getCarWasherContact() {
        return carWasherContact;
    }

    public String getCarType() {
        return carType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPayedAmount() {
        return payedAmount;
    }

    public String getService() {
        return service;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setCarwarsherName(String carwarsherName) {
        this.carwarsherName = carwarsherName;
    }

    public void setCarwasherEmail(String carwasherEmail) {
        this.carwasherEmail = carwasherEmail;
    }

    public void setCarWasherContact(String carWasherContact) {
        this.carWasherContact = carWasherContact;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPayedAmount(String payedAmount) {
        this.payedAmount = payedAmount;
    }

    public void setService(String service) {
        this.service = service;
    }

    public static List<Order> getOrders() {
        return new Select().from(Order.class).execute();
    }

    public static void clearOrders() {
        new Delete().from(Order.class).execute();
    }

}
