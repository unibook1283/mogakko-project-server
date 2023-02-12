package com.example.mogakko.domain.group.dto;

import com.example.mogakko.domain.group.domain.GroupUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupMemberDTO {

    private Long memberId;

    private String nickname;

    private Boolean isMaster;

    public GroupMemberDTO(GroupUser groupUser) {
        this.memberId = groupUser.getUser().getId();
        this.nickname = groupUser.getUser().getNickname();
        this.isMaster = groupUser.getIsMaster();
    }
}
