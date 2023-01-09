package com.example.mogakko.domain.meeting.repository;

import com.example.mogakko.domain.meeting.domain.Meeting;
import com.example.mogakko.domain.meeting.domain.MeetingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
    List<MeetingUser> findByMeeting(Meeting meeting);
}
