package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserLanguage;
import com.example.mogakko.domain.user.domain.UserLocation;
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
@Transactional
@RequiredArgsConstructor
public class UserLocationService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserLocationRepository userLocationRepository;

    public Long prefer(Long userId, Long locationId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));

        Optional<Location> optionalLocation = locationRepository.findById(locationId);
        Location location = optionalLocation.get();     // locationId는 location name으로 찾을것이기 때문에 null이 아님이 보장됨.

        UserLocation userLocation = new UserLocation();
        userLocation.setUser(user);
        userLocation.setLocation(location);

        UserLocation saveUserLocation = userLocationRepository.save(userLocation);

        return saveUserLocation.getId();
    }

    public List<UserLocation> findLocationsOfUser(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));

        return userLocationRepository.findByUser(user);
    }
}
