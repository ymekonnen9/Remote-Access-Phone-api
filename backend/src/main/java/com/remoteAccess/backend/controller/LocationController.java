package com.remoteAccess.backend.controller;

import com.remoteAccess.backend.models.LocationRequest;
import com.remoteAccess.backend.services.LocationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {

    private LocationRequestService locationRequestService;

    @Autowired
    public LocationController(LocationRequestService locationRequestService){
        this.locationRequestService = locationRequestService;
    }

    @PostMapping
    public ResponseEntity<String> captureLocation(@RequestBody LocationRequest location) {
        System.out.println("Received location: " + location);
        locationRequestService.saveLocation(location);
        return ResponseEntity.ok("Location received");
    }
}