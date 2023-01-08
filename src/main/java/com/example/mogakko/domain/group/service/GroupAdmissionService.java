package com.example.mogakko.domain.group.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupAdmission;
import com.example.mogakko.domain.group.dto.GroupAdmissionDTO;
import com.example.mogakko.domain.group.enums.GroupStatus;
import com.example.mogakko.domain.group.exception.AlreadyAppliedException;
import com.example.mogakko.domain.group.exception.NotRecruitingGroupException;
import com.example.mogakko.domain.group.repository.GroupAdmissionRepository;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupAdmissionService {

    private final GroupAdmissionRepository groupAdmissionRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupAdmissionDTO addGroupAdmission(Long groupId, Long userId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = optionalGroup.orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        if (group.getGroupStatus() != GroupStatus.RECRUIT) {
            throw new NotRecruitingGroupException("모집중인 그룹이 아닙니다.");
        }

        Optional<GroupAdmission> optionalGroupAdmission = groupAdmissionRepository.findByGroupAndUser(group, user);
        if (optionalGroupAdmission.isPresent()) {
            throw new AlreadyAppliedException("이미 신청했습니다.");
        }

        GroupAdmission groupAdmission = new GroupAdmission(group, user);

        GroupAdmission saveGroupAdmission = groupAdmissionRepository.save(groupAdmission);

        return new GroupAdmissionDTO(saveGroupAdmission);
    }
}
