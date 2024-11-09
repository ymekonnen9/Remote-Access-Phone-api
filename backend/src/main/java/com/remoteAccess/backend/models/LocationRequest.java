package com.remoteAccess.backend.models;

public class LocationRequest {
    private double latitude;
    private double longitude;
    // getters and setters

    public LocationRequest(){
    }

    public LocationRequest(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
}