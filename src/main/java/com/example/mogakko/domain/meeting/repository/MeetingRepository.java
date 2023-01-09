package com.example.mogakko.domain.meeting.repository;

import com.example.mogakko.domain.meeting.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
