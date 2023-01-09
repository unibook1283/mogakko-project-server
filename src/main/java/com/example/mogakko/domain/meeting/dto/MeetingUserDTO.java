package com.example.mogakko.domain.meeting.dto;

import com.example.mogakko.domain.meeting.domain.MeetingUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MeetingUserDTO {

    private Long meetingUserId;

    private Long meetingId;

    private Long userId;

    private Boolean attendance;

    private Boolean isMaster;

    public MeetingUserDTO(MeetingUser meetingUser) {
        this.meetingUserId = meetingUser.getId();
        this.meetingId = meetingUser.getMeeting().getId();
        this.userId = meetingUser.getUser().getId();
        this.attendance = meetingUser.getAttendance();
        this.isMaster = meetingUser.getIsMaster();
    }
}
