package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserLanguage;
import com.example.mogakko.domain.user.domain.UserLocation;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserLocationRepository;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.example.mogakko.domain.values.domain.Location;
import com.example.mogakko.domain.values.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLocationService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserLocationRepository userLocationRepository;

    @Transactional
    public Long prefer(Long userId, Long locationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // locationId는 location name으로 찾을것이기 때문에 null이 아님이 보장됨.
        Location location = locationRepository.findById(locationId).get();

        UserLocation userLocation = new UserLocation();
        userLocation.setUser(user);
        userLocation.setLocation(location);

        UserLocation saveUserLocation = userLocationRepository.save(userLocation);

        return saveUserLocation.getId();
    }

    public List<UserLocation> findLocationsOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return userLocationRepository.findByUser(user);
    }
}
