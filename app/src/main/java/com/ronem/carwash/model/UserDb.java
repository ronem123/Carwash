package com.ronem.carwash.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ram on 8/15/17.
 */


@Table(name = "users")
public class UserDb extends Model {
    @Column(name = "user_id")
    public int userId;
    @Column(name = "user_type")
    public String userType;
    @Column(name = "user_name")
    public String userName;
    @Column(name = "user_password")
    public String userPassword;
    @Column(name = "full_name")
    public String fullName;
    @Column(name = "contact_no")
    public String contact;
    @Column(name = "car_type")
    public int carType;
    @Column(name = "latitude")
    public String latitude;
    @Column(name = "longitude")
    public String longitude;


    public UserDb() {
        super();
    }

    public static List<UserDb> getUsers() {
        return new Select().from(UserDb.class).execute();
    }

}
