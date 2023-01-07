package com.example.mogakko.domain.group.service;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.group.dto.GroupMemberDTO;
import com.example.mogakko.domain.group.dto.MyGroupDTO;
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
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = optionalGroup.orElseThrow(() -> new IllegalArgumentException("잘못된 groupId"));

        List<GroupUser> groupUsers = groupUserRepository.findByGroup(group);

        return groupUsers.stream()
                .map(groupUser -> new GroupMemberDTO(groupUser))
                .collect(Collectors.toList());
    }

    public List<MyGroupDTO> getGroupListOfUser(Long memberId) {
        Optional<User> userOptional = userRepository.findById(memberId);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        return user.getGroupUsers().stream()
                .map(groupUser -> {
                    Group group = groupUser.getGroup();
                    MyGroupDTO myGroupDTO = new MyGroupDTO();
                    myGroupDTO.setGroupId(group.getId());
                    myGroupDTO.setGroupStatus(group.getGroupStatus());

                    Optional<GroupUser> optionalMasterGroupUser = groupUserRepository.findByGroupAndIsMaster(group, true);
                    // 일단 get() 해놨는데, groupMaster가 회원탈퇴했으면?
                    GroupUser masterGroupUser = optionalMasterGroupUser.get();
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

}
