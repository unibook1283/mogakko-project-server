package com.example.mogakko.domain.group.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.group.dto.*;
import com.example.mogakko.domain.group.enums.GroupStatus;
import com.example.mogakko.domain.group.exception.IsNotGroupMasterException;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.group.repository.GroupUserRepository;
import com.example.mogakko.domain.post.domain.Mogakko;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.enums.Type;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    public List<GroupMemberDTO> findGroupMembersByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        List<GroupUser> groupUsers = groupUserRepository.findByGroup(group);

        return groupUsers.stream()
                .map(groupUser -> new GroupMemberDTO(groupUser))
                .collect(Collectors.toList());
    }

    public List<MyGroupDTO> getGroupListOfUser(Long memberId) {
        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        return user.getGroupUsers().stream()
                .map(groupUser -> {
                    Group group = groupUser.getGroup();
                    MyGroupDTO myGroupDTO = new MyGroupDTO();
                    myGroupDTO.setGroupId(group.getId());
                    myGroupDTO.setGroupStatus(group.getGroupStatus());

                    GroupUser masterGroupUser = groupUserRepository.findByGroupAndIsMaster(group, true).get();
                    myGroupDTO.setGroupMaster(masterGroupUser.getUser().getNickname());

                    Post post = group.getPost();
                    Type type = Type.valueOf(post.getDtype());
                    myGroupDTO.setType(type);
                    myGroupDTO.setTitle(post.getTitle());
                    if (type == Type.MOGAKKO) {
                        Mogakko mogakko = (Mogakko) post;
                        myGroupDTO.setTerm(mogakko.getTerm());
                    }
                    return myGroupDTO;
                })
                .collect(Collectors.toList());

    }

    public void deleteGroupMember(Long groupId, Long memberId, UserIdDTO userIdDTO) {
        User deleteUser = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        User caller = userRepository.findById(userIdDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        GroupUser masterGroupUser = groupUserRepository.findByGroupAndIsMaster(group, true).get();

        if (caller.getId() != masterGroupUser.getUser().getId()) {
            throw new IsNotGroupMasterException("그룹장만 실행할 수 있습니다.");
        }

        groupUserRepository.deleteByGroupAndUser(group, deleteUser);
    }

    public GroupStatusResponseDTO getGroupStatus(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        return new GroupStatusResponseDTO(group);
    }

    public GroupStatusResponseDTO setGroupStatus(Long groupId, GroupStatus groupStatus) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        group.setGroupStatus(groupStatus);

        return new GroupStatusResponseDTO(group);
    }
}
