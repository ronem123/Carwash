package com.ronem.carwash.model;

/**
 * Created by ronem on 8/8/17.
 */

public class ServiceType {
    private String serviceTypeId;
    private String serviceType;
    private String serviceCharge;

    public ServiceType(String serviceTypeId, String serviceType, String serviceCharge) {
        this.serviceTypeId = serviceTypeId;
        this.serviceType = serviceType;
        this.serviceCharge = serviceCharge;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }
}
