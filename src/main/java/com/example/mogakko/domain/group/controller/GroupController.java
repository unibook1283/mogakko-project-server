package com.example.mogakko.domain.group.controller;

import com.example.mogakko.domain.group.dto.GroupMemberDTO;
import com.example.mogakko.domain.group.dto.MyGroupDTO;
import com.example.mogakko.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/groups/{groupId}/members")
    public List<GroupMemberDTO> getGroupMembers(@PathVariable Long groupId) {
        return groupService.findGroupMembersByGroupId(groupId);
    }

    @GetMapping("/members/{memberId}/groups")
    public List<MyGroupDTO> getGroupListOfUser(@PathVariable Long memberId) {
        return groupService.getGroupListOfUser(memberId);
    }
}
