package com.example.mogakko.domain.meeting.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.group.repository.GroupUserRepository;
import com.example.mogakko.domain.meeting.domain.Meeting;
import com.example.mogakko.domain.meeting.domain.MeetingUser;
import com.example.mogakko.domain.meeting.dto.CreateMeetingRequestDTO;
import com.example.mogakko.domain.meeting.dto.CreateMeetingResponseDTO;
import com.example.mogakko.domain.meeting.repository.MeetingRepository;
import com.example.mogakko.domain.meeting.repository.MeetingUserRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MeetingUserRepository meetingUserRepository;

    public CreateMeetingResponseDTO createMeeting(Long groupId, CreateMeetingRequestDTO createMeetingRequestDTO) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = optionalGroup.orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        Optional<User> optionalUser = userRepository.findById(createMeetingRequestDTO.getMemberId());
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        MeetingUser meetingUser = new MeetingUser();
        meetingUser.setUser(user);
        meetingUser.setAttendance(true);
        meetingUser.setIsMaster(true);
        MeetingUser saveMeetingUser = meetingUserRepository.save(meetingUser);

        Meeting meeting = Meeting.createMeeting(group, saveMeetingUser, createMeetingRequestDTO.getDate(), createMeetingRequestDTO.getPlace(), createMeetingRequestDTO.getTime());
        Meeting saveMeeting = meetingRepository.save(meeting);

        return new CreateMeetingResponseDTO(saveMeeting);
    }

}
