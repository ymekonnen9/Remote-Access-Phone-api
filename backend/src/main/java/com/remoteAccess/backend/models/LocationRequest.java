package com.remoteAccess.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class LocationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double latitude;
    private double longitude;
    // getters and setters
    public LocationRequest(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public LocationRequest(){
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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