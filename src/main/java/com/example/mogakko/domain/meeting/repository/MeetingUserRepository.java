package com.example.mogakko.domain.meeting.repository;

import com.example.mogakko.domain.meeting.domain.Meeting;
import com.example.mogakko.domain.meeting.domain.MeetingUser;
import com.example.mogakko.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingUserRepository extends JpaRepository<MeetingUser, Long> {
    List<MeetingUser> findByMeeting(Meeting meeting);
    void deleteAllByMeeting(Meeting meeting);
    Optional<MeetingUser> findByMeetingAndUser(Meeting meeting, User user);
}
