package com.example.mogakko.domain.group.dto;

import com.example.mogakko.domain.group.domain.GroupAdmission;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupAdmissionDTO {

    private Long groupAdmissionId;

    private Long groupId;

    private Long userId;

    public GroupAdmissionDTO(GroupAdmission groupAdmission) {
        this.groupAdmissionId = groupAdmission.getId();
        this.groupId = groupAdmission.getGroup().getId();
        this.userId = groupAdmission.getUser().getId();
    }
}
