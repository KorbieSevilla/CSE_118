package com.zybooks.rentadrivemobile2;

public class LatLng{

    public double latitude;
    public double longitude;

    public LatLng(){}

    public LatLng(double v, double v1) {
        latitude = v;
        longitude = v1;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }


}
