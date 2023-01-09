package com.example.mogakko.domain.meeting.repository;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByGroup(Group group);
}
