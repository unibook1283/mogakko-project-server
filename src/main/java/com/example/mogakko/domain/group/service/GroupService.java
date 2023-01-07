package com.example.mogakko.domain.group.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.group.dto.GroupMemberDTO;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.group.repository.GroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;

    public List<GroupMemberDTO> findGroupMembersByGroupId(Long groupId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = optionalGroup.orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        List<GroupUser> groupUsers = groupUserRepository.findByGroup(group);

        return groupUsers.stream()
                .map(groupUser -> new GroupMemberDTO(groupUser))
                .collect(Collectors.toList());
    }

}
