package com.zybooks.rentadrivemobile2;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class Posting implements Serializable {
    public List<LatLng> addresses;
    public String description;
    public float price;
    public double lat;
    public double longitude;


    public Posting(List<LatLng> addressList, String desc, float price){
        this.addresses = addressList;
        this.description = desc;
        this.price = price;

        lat = addressList.get(0).latitude;
        longitude = addressList.get(0).longitude;
    }

    public List<LatLng> getAddresses(){
        return this.addresses;
    }

    public String getDescription(){
        return this.description;
    }

    public float getPrice(){
        return this.price;
    }


}
