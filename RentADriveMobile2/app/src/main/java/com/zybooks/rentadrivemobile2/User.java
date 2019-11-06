package com.zybooks.rentadrivemobile2;

import java.io.Serializable;

public class User implements Serializable {
    public String userName, userEmail, userPhone;

    public User(){

    }

    public User(String name, String email, String phone){
        this.userName = name;
        this.userEmail = email;
        this.userPhone = phone;
    }
}
