package com.remoteAccess.backend.controller;

import com.remoteAccess.backend.services.LocationRequestService;
import com.remoteAccess.backend.models.LocationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    private LocationRequestService locationRequestService;

    @Autowired
    public LocationController(LocationRequestService locationRequestService){
        this.locationRequestService = locationRequestService;
    }

    @PostMapping("/capturelocation")
    public ResponseEntity<String> captureLocation(@RequestBody LocationRequest locationRequest) {
        locationRequestService.saveLocation(locationRequest);
        System.out.println("Received location: " + locationRequest.getLatitude() + ", " + locationRequest.getLongitude());
        return ResponseEntity.ok("Location received");
    }
}
