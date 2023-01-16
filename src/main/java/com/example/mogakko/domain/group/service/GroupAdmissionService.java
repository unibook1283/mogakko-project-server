package com.example.mogakko.domain.group.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupAdmission;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.group.dto.ApplicantsDTO;
import com.example.mogakko.domain.group.dto.GroupAdmissionDTO;
import com.example.mogakko.domain.group.enums.GroupStatus;
import com.example.mogakko.domain.group.exception.AlreadyAppliedException;
import com.example.mogakko.domain.group.exception.GroupNotFoundException;
import com.example.mogakko.domain.group.exception.NotRecruitingGroupException;
import com.example.mogakko.domain.group.repository.GroupAdmissionRepository;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.group.repository.GroupUserRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupAdmissionService {

    private final GroupAdmissionRepository groupAdmissionRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupUserRepository groupUserRepository;

    @Transactional
    public GroupAdmissionDTO addGroupAdmission(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (group.getGroupStatus() != GroupStatus.RECRUIT) {
            throw new NotRecruitingGroupException();
        }

        Optional<GroupAdmission> optionalGroupAdmission = groupAdmissionRepository.findByGroupAndUser(group, user);
        if (optionalGroupAdmission.isPresent()) {
            throw new AlreadyAppliedException();
        }

        GroupAdmission groupAdmission = new GroupAdmission(group, user);

        GroupAdmission saveGroupAdmission = groupAdmissionRepository.save(groupAdmission);

        return new GroupAdmissionDTO(saveGroupAdmission);
    }

    public List<ApplicantsDTO> findApplicantsOfGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        List<GroupAdmission> groupAdmissions = groupAdmissionRepository.findByGroup(group);

        return groupAdmissions.stream()
                .map(groupAdmission -> {
                    ApplicantsDTO applicantsDTO = new ApplicantsDTO();
                    applicantsDTO.setUserId(groupAdmission.getUser().getId());
                    applicantsDTO.setNickname(groupAdmission.getUser().getNickname());
                    return applicantsDTO;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void acceptApplicant(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        groupAdmissionRepository.deleteByGroupAndUser(group, user);

        groupUserRepository.save(GroupUser.createGroupUser(group, user));
    }

    @Transactional
    public void rejectApplicant(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        groupAdmissionRepository.deleteByGroupAndUser(group, user);
    }
}
