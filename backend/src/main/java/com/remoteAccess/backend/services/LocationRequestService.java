package com.remoteAccess.backend.services;

import com.remoteAccess.backend.models.LocationRequest;
import com.remoteAccess.backend.repositories.LocationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationRequestService {

    private LocationRequestRepository locationRequestRepository;

    @Autowired
    public LocationRequestService(LocationRequestRepository locationRequestRepository){
        this.locationRequestRepository = locationRequestRepository;
    }

    public void saveLocation(LocationRequest locationReques){
        LocationRequest saveable = new LocationRequest();

        saveable.setLatitude(locationReques.getLatitude());
        saveable.setLongitude(locationReques.getLongitude());

        locationRequestRepository.save(saveable);
    }
}
