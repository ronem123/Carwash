package com.ronem.carwash.model;

/**
 * Created by ronem on 8/1/17.
 */

public class NavItem {
    private int icon;
    private String title;

    public NavItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}
