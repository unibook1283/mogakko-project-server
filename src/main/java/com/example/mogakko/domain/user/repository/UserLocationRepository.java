package com.example.mogakko.domain.user.repository;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    List<UserLocation> findByUser(User user);
}
