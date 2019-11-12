package com.zybooks.rentadrivemobile2;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    public String userName, userEmail, userPhone;
    public ArrayList userListings;

    public User(){

    }
    

    public User(String name, String email, String phone, ArrayList listings){
        this.userName = name;
        this.userEmail = email;
        this.userPhone = phone;
        this.userListings = listings;
    }
}
