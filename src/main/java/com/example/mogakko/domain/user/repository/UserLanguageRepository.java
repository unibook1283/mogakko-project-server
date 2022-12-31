package com.example.mogakko.domain.user.repository;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLanguageRepository extends JpaRepository<UserLanguage, Long> {
    List<UserLanguage> findByUser(User user);
}
