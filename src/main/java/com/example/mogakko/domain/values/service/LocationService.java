package com.example.mogakko.domain.values.service;

import com.example.mogakko.domain.values.domain.Location;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 locationId"));
        return new LocationDTO(location);
    }

    public List<LocationDTO> findAll() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(location -> new LocationDTO(location))
                .collect(Collectors.toList());
    }
}
