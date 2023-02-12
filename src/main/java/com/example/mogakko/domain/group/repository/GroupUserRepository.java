package com.example.mogakko.domain.group.repository;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    List<GroupUser> findByGroup(Group group);
    Optional<GroupUser> findByGroupAndIsMaster(Group group, Boolean isMaster);
    void deleteByGroupAndUser(Group group, User user);
    Optional<GroupUser> findByGroupAndUser(Group group, User user);
}
