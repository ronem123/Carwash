package com.ronem.carwash.utils;

import com.ronem.carwash.R;
import com.ronem.carwash.model.Address;
import com.ronem.carwash.model.CarType;
import com.ronem.carwash.model.CarWashLocation;
import com.ronem.carwash.model.NavItem;
import com.ronem.carwash.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronem on 8/1/17.
 */

public class MetaData {

    public static final String MSG_EMPTY_FIELD = "Field should not be empty";
    public static final String MSG_PASSWORD_NOT_MATCHED = "Sorry ! But password not matched";
    public static final String MSG_EMAIL_NOT_FOUND = "Sorry But This email not found in our database";

    public static NavItem[] getnavItems() {
        NavItem[] items = new NavItem[]{
                new NavItem(R.mipmap.ic_launcher, "Orders")
        };

        return items;
    }

    public static List<CarWashLocation> getLocations() {
        List<CarWashLocation> ls = new ArrayList<>();

        ls.add(new CarWashLocation(1, "Car wash 1", "East North", 27.651371, 85.3277125));
        ls.add(new CarWashLocation(2, "Car wash 2", "East South", 27.637367, 85.3278748));
        ls.add(new CarWashLocation(3, "Car wash 3", "Dhapakhel Cloths", 27.637665, 85.3216349));
        ls.add(new CarWashLocation(4, "Car wash 4", "Tewa clothes store", 27.639012, 85.3258318));

        return ls;
    }

    public static List<CarType> getCarType() {
        List<CarType> c = new ArrayList<>();

        c.add(new CarType(1, "Small", "35SR"));
        c.add(new CarType(2, "Medium", "50SR"));
        c.add(new CarType(3, "Large", "80SR"));

        return c;
    }

    public static List<Address> getAddress() {
        List<Address> ad = new ArrayList<>();

        ad.add(new Address(1, "Al Mohammadiyah, Riyadh1"));
        ad.add(new Address(2, "Al Mohammadiyah, Riyadh2"));
        ad.add(new Address(3, "Al Mohammadiyah, Riyadh3"));
        ad.add(new Address(4, "Al Mohammadiyah, Riyadh4"));
        return ad;
    }

    public static List<PaymentMethod> getpaymentMEthods() {
        List<PaymentMethod> p = new ArrayList<>();
        p.add(new PaymentMethod(false, 1, "Credit Card", R.mipmap.ic_credit_card_black_24dp, R.mipmap.ic_check_box_outline_blank_black_24dp));
        p.add(new PaymentMethod(false, 2, "Cash Payment", R.mipmap.ic_description_black_24dp, R.mipmap.ic_check_box_outline_blank_black_24dp));
        p.add(new PaymentMethod(false, 3, "Coupon", R.mipmap.ic_card_giftcard_black_24dp, R.mipmap.ic_check_box_outline_blank_black_24dp));
        p.add(new PaymentMethod(false, 4, "Monthly Payment", R.mipmap.ic_perm_contact_calendar_black_24dp, R.mipmap.ic_check_box_outline_blank_black_24dp));
        return p;
    }
}
