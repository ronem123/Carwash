package com.ronem.carwash.utils;

import com.ronem.carwash.R;
import com.ronem.carwash.model.NavItem;

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
}
