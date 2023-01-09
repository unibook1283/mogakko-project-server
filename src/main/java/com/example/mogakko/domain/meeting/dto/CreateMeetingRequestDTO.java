package com.example.mogakko.domain.meeting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMeetingRequestDTO {

    private Long memberId;

    private String date;

    private String place;

    private String time;

}
