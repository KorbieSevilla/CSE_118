package com.zybooks.rentadrivemobile2;

import java.io.Serializable;
import java.util.List;

public class Posting implements Serializable {
    public List<com.zybooks.rentadrivemobile2.LatLng> addresses;
    public String description;
    public float price;
    public double lat;
    public double longitude;


    public Posting(List<LatLng> addressList, String desc, float price){
        this.addresses = addressList;
        this.description = desc;
        this.price = price;

        lat = addressList.get(0).getLatitude();
        longitude = addressList.get(0).getLongitude();
    }

    public List<com.zybooks.rentadrivemobile2.LatLng> getAddresses(){
        return this.addresses;
    }

    public String getDescription(){
        return this.description;
    }

    public float getPrice(){
        return this.price;
    }


}
