package com.example.mogakko.domain.user.repository;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserOccupation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOccupationRepository extends JpaRepository<UserOccupation, Long> {
    List<UserOccupation> findByUser(User user);
}
