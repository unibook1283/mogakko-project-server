package com.example.mogakko.domain.group.repository;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupAdmission;
import com.example.mogakko.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupAdmissionRepository extends JpaRepository<GroupAdmission, Long> {
    Optional<GroupAdmission> findByGroupAndUser(Group group, User user);
}
