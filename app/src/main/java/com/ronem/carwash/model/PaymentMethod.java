package com.ronem.carwash.model;

/**
 * Created by ronem on 8/2/17.
 */

public class PaymentMethod {
    private boolean isSelected;
    private int payId;
    private String paymentMethod;
    private int icon;
    private int chkbox;

    public PaymentMethod(boolean isSelected, int payId, String paymentMethod, int icon, int chkbox) {
        this.isSelected = isSelected;
        this.payId = payId;
        this.paymentMethod = paymentMethod;
        this.icon = icon;
        this.chkbox = chkbox;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getPayId() {
        return payId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public int getIcon() {
        return icon;
    }

    public int getChkbox() {
        return chkbox;
    }
}
