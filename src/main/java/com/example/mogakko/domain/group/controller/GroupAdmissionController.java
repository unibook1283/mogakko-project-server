package com.example.mogakko.domain.group.controller;

import com.example.mogakko.domain.group.dto.GroupAdmissionDTO;
import com.example.mogakko.domain.group.service.GroupAdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GroupAdmissionController {

    private final GroupAdmissionService groupAdmissionService;

    @PostMapping("/groups/{groupId}/users/{userId}")
    public GroupAdmissionDTO applyForAdmission(@PathVariable Long groupId, @PathVariable Long userId) {
        return groupAdmissionService.addGroupAdmission(groupId, userId);
    }
}
