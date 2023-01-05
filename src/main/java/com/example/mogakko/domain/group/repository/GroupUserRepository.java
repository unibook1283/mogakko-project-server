package com.example.mogakko.domain.group.repository;

import com.example.mogakko.domain.group.domain.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
}
