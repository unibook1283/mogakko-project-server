package com.example.mogakko.domain.meeting.dto;

import com.example.mogakko.domain.meeting.domain.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MeetingDTO {

    private Long meetingId;

    private String date;

    private String place;

    private String time;

    private List<AttendanceDTO> attendanceList;

    public MeetingDTO(Meeting meeting, List<AttendanceDTO> attendanceDTOS) {
        this.meetingId = meeting.getId();
        this.date = meeting.getDate();
        this.place = meeting.getPlace();
        this.time = meeting.getTime();
        this.attendanceList = attendanceDTOS;
    }
}
