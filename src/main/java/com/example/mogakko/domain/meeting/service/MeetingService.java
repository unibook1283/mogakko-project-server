package com.example.mogakko.domain.meeting.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.exception.GroupNotFoundException;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.meeting.domain.Meeting;
import com.example.mogakko.domain.meeting.domain.MeetingUser;
import com.example.mogakko.domain.meeting.dto.*;
import com.example.mogakko.domain.meeting.exception.MeetingNotFoundException;
import com.example.mogakko.domain.meeting.repository.MeetingRepository;
import com.example.mogakko.domain.meeting.repository.MeetingUserRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MeetingUserRepository meetingUserRepository;

    @Transactional
    public CreateMeetingResponseDTO createMeeting(Long groupId, CreateMeetingRequestDTO createMeetingRequestDTO) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        User user = userRepository.findById(createMeetingRequestDTO.getMemberId())
                .orElseThrow(UserNotFoundException::new);

        MeetingUser meetingUser = new MeetingUser();
        meetingUser.setUser(user);
        meetingUser.setAttendance(true);
        meetingUser.setIsMaster(true);
        MeetingUser saveMeetingUser = meetingUserRepository.save(meetingUser);

        Meeting meeting = Meeting.createMeeting(group, saveMeetingUser, createMeetingRequestDTO.getDate(), createMeetingRequestDTO.getPlace(), createMeetingRequestDTO.getTime());
        Meeting saveMeeting = meetingRepository.save(meeting);

        return new CreateMeetingResponseDTO(saveMeeting);
    }

    public List<MeetingDTO> findMeetingListOfGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        List<Meeting> meetings = meetingRepository.findByGroup(group);
        return meetings.stream()
                .map(meeting -> {
                    List<MeetingUser> meetingUsers = meeting.getMeetingUsers();
                    List<AttendantDTO> attendantDTOS = getAttendanceDTOS(meetingUsers);

                    return new MeetingDTO(meeting, attendantDTOS);
                })
                .collect(Collectors.toList());
    }

    private List<AttendantDTO> getAttendanceDTOS(List<MeetingUser> meetingUsers) {
        List<AttendantDTO> attendantDTOS = meetingUsers.stream()
                .map(meetingUser -> {
                    User user = meetingUser.getUser();
                    return new AttendantDTO(user.getId(), user.getNickname(), meetingUser.getIsMaster());
                })
                .collect(Collectors.toList());
        return attendantDTOS;
    }

    @Transactional
    public void deleteMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(MeetingNotFoundException::new);

        meetingUserRepository.deleteAllByMeeting(meeting);
        meetingRepository.delete(meeting);
    }


    @Transactional
    public MeetingUserDTO setMeetingAttendance(Long meetingId, Long memberId, Boolean attendance) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(MeetingNotFoundException::new);

        User user = userRepository.findById(memberId)
                .orElseThrow(UserNotFoundException::new);

        Optional<MeetingUser> optionalMeetingUser = meetingUserRepository.findByMeetingAndUser(meeting, user);

        MeetingUser meetingUser;
        if (optionalMeetingUser.isPresent()) {
            meetingUser = optionalMeetingUser.get();
            meetingUser.setAttendance(attendance);
        }
        else {
            meetingUser = new MeetingUser();
            meetingUser.setMeeting(meeting);
            meetingUser.setUser(user);
            meetingUser.setAttendance(attendance);
            meetingUser.setIsMaster(false);
            meetingUser = meetingUserRepository.save(meetingUser);
        }
        return new MeetingUserDTO(meetingUser);
    }
}
