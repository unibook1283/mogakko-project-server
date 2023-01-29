package com.example.mogakko.domain.group.controller;

import com.example.mogakko.domain.group.dto.AcceptDTO;
import com.example.mogakko.domain.group.dto.ApplicantsDTO;
import com.example.mogakko.domain.group.dto.GroupAdmissionDTO;
import com.example.mogakko.domain.group.service.GroupAdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GroupAdmissionController {

    private final GroupAdmissionService groupAdmissionService;

    @PostMapping("/groups/{groupId}/application/users/{userId}")
    public GroupAdmissionDTO applyForAdmission(@PathVariable Long groupId, @PathVariable Long userId) {
        return groupAdmissionService.addGroupAdmission(groupId, userId);
    }

    @GetMapping("/groups/{groupId}/applicants")
    public List<ApplicantsDTO> getApplicantsOfGroup(@PathVariable Long groupId) {
        return groupAdmissionService.findApplicantsOfGroup(groupId);
    }

    @PostMapping("/groups/{groupId}/applicants/users/{userId}")
    public void acceptApplicantOfGroup(@PathVariable Long groupId, @PathVariable Long userId, @RequestBody AcceptDTO acceptDTO) {
        if (acceptDTO.getAccept())
            groupAdmissionService.acceptApplicant(groupId, userId);
        else
            groupAdmissionService.rejectApplicant(groupId, userId);
    }
}
