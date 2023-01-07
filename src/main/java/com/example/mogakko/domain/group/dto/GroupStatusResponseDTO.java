package com.example.mogakko.domain.group.dto;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.enums.GroupStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupStatusResponseDTO {

    private Long groupId;

    private GroupStatus groupStatus;

    public GroupStatusResponseDTO(Group group) {
        this.groupId = group.getId();
        this.groupStatus = group.getGroupStatus();
    }

}
