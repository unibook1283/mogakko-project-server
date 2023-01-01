package com.example.mogakko.domain.values.service;

import com.example.mogakko.domain.values.domain.Location;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public Long saveLocation(LocationDTO locationDTO) {
        Location location = locationRepository.save(locationDTO.toEntity());
        return location.getId();
    }

    public LocationDTO findOne(Long locationId) {
        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        if (optionalLocation.isPresent()) {
            return new LocationDTO(optionalLocation.get());
        } else {
            return null;
        }
    }
}
