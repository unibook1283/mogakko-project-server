package com.example.mogakko.domain.values.controller;

import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.LocationListDTO;
import com.example.mogakko.domain.values.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/locations")
    public LocationListDTO getLocations() {
        List<LocationDTO> locations = locationService.findAll();
        return new LocationListDTO(locations);
    }

}
