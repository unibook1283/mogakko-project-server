package com.example.mogakko.domain.meeting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendantDTO {

    private Long memberId;

    private String nickname;

    private Boolean isMaster;

}
