package com.example.mogakko.domain.group.dto;

import com.example.mogakko.domain.group.enums.GroupStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupStatusRequestDTO {

    private GroupStatus groupStatus;

}
