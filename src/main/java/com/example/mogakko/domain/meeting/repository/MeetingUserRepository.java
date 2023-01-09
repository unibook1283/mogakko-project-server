package com.example.mogakko.domain.meeting.repository;

import com.example.mogakko.domain.meeting.domain.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
}
