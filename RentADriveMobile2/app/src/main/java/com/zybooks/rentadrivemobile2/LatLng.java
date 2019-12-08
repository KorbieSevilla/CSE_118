package com.zybooks.rentadrivemobile2;

//Custom LatLng object with an empty constructor so it can be retrieved from the database
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
