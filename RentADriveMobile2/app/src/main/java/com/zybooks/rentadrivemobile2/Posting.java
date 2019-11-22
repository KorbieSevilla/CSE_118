package com.zybooks.rentadrivemobile2;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Posting {
    public List<LatLng> addresses;
    public String description;

    public Posting(List<LatLng> addressList, String desc){
        this.addresses = addressList;
        this.description = desc;
    }

}
