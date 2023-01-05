package com.example.mogakko.domain.group.repository;

import com.example.mogakko.domain.group.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
