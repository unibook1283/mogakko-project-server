package com.example.mogakko.domain.meeting.dto;

import com.example.mogakko.domain.meeting.domain.Meeting;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMeetingResponseDTO {

    private Long meetingId;

    private Long groupId;

    private String date;

    private String place;

    private String time;

    public CreateMeetingResponseDTO(Meeting meeting) {
        this.meetingId = meeting.getId();
        this.groupId = meeting.getGroup().getId();
        this.date = meeting.getDate();
        this.place = meeting.getPlace();
        this.time = meeting.getTime();
    }
}
