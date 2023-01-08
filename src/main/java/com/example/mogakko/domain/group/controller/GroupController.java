package com.example.mogakko.domain.group.controller;

import com.example.mogakko.domain.group.dto.*;
import com.example.mogakko.domain.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/groups/{groupId}/release/members/{memberId}")
    public void releaseGroupMember(@PathVariable Long groupId, @PathVariable Long memberId, @RequestBody UserIdDTO userIdDTO) {
        groupService.deleteGroupMember(groupId, memberId, userIdDTO);
    }

    @GetMapping("/groups/{groupId}/status")
    public GroupStatusResponseDTO getGroupStatus(@PathVariable Long groupId) {
        return groupService.getGroupStatus(groupId);
    }

    @PostMapping("/groups/{groupId}/status")
    public GroupStatusResponseDTO setGroupStatus(@PathVariable Long groupId, @RequestBody GroupStatusRequestDTO groupStatusRequestDTO) {
        return groupService.setGroupStatus(groupId, groupStatusRequestDTO.getGroupStatus());
    }
}
