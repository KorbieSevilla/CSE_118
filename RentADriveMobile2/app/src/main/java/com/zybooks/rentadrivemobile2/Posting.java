package com.zybooks.rentadrivemobile2;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class Posting implements Serializable {
    public List<LatLng> addresses;
    public String description;
    public float price;

    public Posting(List<LatLng> addressList, String desc, float price){
        this.addresses = addressList;
        this.description = desc;
        this.price = price;
    }

}
