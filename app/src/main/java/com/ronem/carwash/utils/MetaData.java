package com.ronem.carwash.utils;

import com.ronem.carwash.R;
import com.ronem.carwash.model.CarWashLocation;
import com.ronem.carwash.model.NavItem;

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
}
