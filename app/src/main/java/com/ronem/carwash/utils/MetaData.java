package com.ronem.carwash.utils;

import com.ronem.carwash.R;
import com.ronem.carwash.model.Address;
import com.ronem.carwash.model.CarType;
import com.ronem.carwash.model.DeliveredStationLocation;
import com.ronem.carwash.model.NavItem;
import com.ronem.carwash.model.PaymentMethod;
import com.ronem.carwash.model.ServiceType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronem on 8/1/17.
 */

public class MetaData {

    public static final String MSG_EMPTY_FIELD = "Field should not be empty";
    public static final String MSG_PASSWORD_NOT_MATCHED = "Sorry ! But password not matched";
    public static final String MSG_EMAIL_NOT_FOUND = "Sorry But This email not found in our database";
    public static final String MSG_SELECT_CAR_TYPE = "Please select what type of car you have";
    public static final String SELECT_CAR_TYPE = "Choose car type";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_ORDER_TYPE = "order_type";
    public  static final String ORDER_TYPE_DELIVERD = "Delivered";
    public  static final String ORDER_TYPE_STATION = "Station";

    public static NavItem[] getnavItems() {
        NavItem[] items = new NavItem[]{
                new NavItem(R.mipmap.ic_add_shopping_cart_white_24dp, "Orders"),
                new NavItem(R.mipmap.ic_notifications_black_24dp, "Notifications"),
                new NavItem(R.mipmap.ic_edit_black_24dp, "Edit Profile"),
                new NavItem(R.mipmap.ic_power_settings_new_white_24dp, "Log out")
        };

        return items;
    }

    public static List<DeliveredStationLocation> getStations() {
        List<DeliveredStationLocation> ls = new ArrayList<>();

        ls.add(new DeliveredStationLocation(1, "John Cena", "John Cena Station Address", "123456789", 3, 27.651371, 85.3277125));
        ls.add(new DeliveredStationLocation(2, "Batista", "Batista Station Address", "987654321", 4, 27.637367, 85.3278748));
        ls.add(new DeliveredStationLocation(3, "John Michel", "John Michel Station Address", "123459876", 1, 27.637665, 85.3216349));
        ls.add(new DeliveredStationLocation(4, "Under Taker", "Under Taker Station,Address", "54312789", 3, 27.639012, 85.3258318));

        return ls;
    }

    public static List<DeliveredStationLocation> getDeliveredLocations() {
        List<DeliveredStationLocation> ls = new ArrayList<>();

        ls.add(new DeliveredStationLocation(1, "John Cena", "John Cena Address", "123456789", 3, 27.651371, 85.3277125));
        ls.add(new DeliveredStationLocation(2, "Batista", "Batista Address", "987654321", 4, 27.637367, 85.3278748));
        ls.add(new DeliveredStationLocation(3, "John Michel", "John Michel Address", "123459876", 2, 27.637665, 85.3216349));
        ls.add(new DeliveredStationLocation(4, "Under Taker", "Under Taker Address", "54312789", 1, 27.639012, 85.3258318));

        return ls;
    }

    public static List<CarType> getCarType() {
        List<CarType> c = new ArrayList<>();

        c.add(new CarType(1, "Small", "35SR"));
        c.add(new CarType(2, "Large", "50SR"));
        c.add(new CarType(3, "X-Large", "80SR"));

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

    public static List<ServiceType> getServiceType() {
        List<ServiceType> s = new ArrayList<>();
        s.add(new ServiceType("1", "Car wash", "20SR"));
        s.add(new ServiceType("2", "Car Polishing", "100SR"));
        s.add(new ServiceType("3", "Car Maintenance", "30SR"));
        s.add(new ServiceType("4", "Car Tyre Repair", "20SR"));
        return s;
    }
}
