package com.example.mogakko.domain.group.controller;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.group.dto.GroupStatusRequestDTO;
import com.example.mogakko.domain.group.dto.UserIdDTO;
import com.example.mogakko.domain.group.enums.GroupStatus;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.group.repository.GroupUserRepository;
import com.example.mogakko.domain.group.service.GroupService;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.enums.Type;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.user.controller.SessionConst;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected MockHttpSession session;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private PostRepository postRepository;

    private Group group;
    private User user1, user2;
    private Post post;

    @BeforeEach
    void setupDatabase() {
        user1 = userRepository.save(createUser("qwer1234", "qwer1234!", "담비", "안녕하세요.", "01012341234", "qwer.github.com", "img1"));
        user2 = userRepository.save(createUser("asdf1234", "asdf1234!", "단비", "안녕하세요.", "01012341235", "asdf.github.com", "img2"));
        group = groupRepository.save(createGroup());

        GroupUser groupUser1 = GroupUser.createGroupUser(group, user1);
        groupUser1.setIsMaster(true);
        GroupUser saveGroupUser1 = groupUserRepository.save(groupUser1);
        GroupUser saveGroupUser2 = groupUserRepository.save(GroupUser.createGroupUser(group, user2));
        user1.getGroupUsers().add(saveGroupUser1);
        user2.getGroupUsers().add(saveGroupUser2);
        group.getGroupUsers().add(saveGroupUser1);
        group.getGroupUsers().add(saveGroupUser2);

        post = postRepository.save(createPost("PROJECT", user1, group, "제목", "내용"));
        post.setGroup(group);

        session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_USER, user2.getId());
    }

    private Post createPost(String type, User user, Group group, String title, String content) {
        Post newPost = new Post();
        newPost.setDtype(type);
        newPost.setUser(user);
        newPost.setGroup(group);
        newPost.setTitle(title);
        newPost.setContent(content);
        return newPost;
    }

    private Group createGroup() {
        Group newGroup = new Group();
        newGroup.setGroupStatus(GroupStatus.RECRUIT);
        return newGroup;
    }

    private User createUser(String username, String password, String nickname, String oneLineIntroduction, String phoneNumber, String githubAddress, String picture) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setOneLineIntroduction(oneLineIntroduction);
        user.setPhoneNumber(phoneNumber);
        user.setGithubAddress(githubAddress);
        user.setPicture(picture);
        user.setAdmin(false);
        return user;
    }




    @Test
    @DisplayName("groupId로 해당 그룹에 속한 User 리스트를 조회한다.")
    void getGroupMembersHttpRequest() throws Exception {
        mockMvc.perform(get("/api/groups/{groupId}/members", group.getId())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("user가 속한 group list를 조회한다.")
    void getGroupListOfUserHttpRequest() throws Exception {
        mockMvc.perform(get("/api/members/{memberId}/groups", user1.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].groupId").value(group.getId()))
                .andExpect(jsonPath("$[0].groupStatus").value(group.getGroupStatus().toString()))
                .andExpect(jsonPath("$[0].type").value(Type.PROJECT.toString()))
                .andExpect(jsonPath("$[0].title").value("제목"))
                .andExpect(jsonPath("$[0].groupMaster").value(user1.getNickname()));
    }

    @Test
    @DisplayName("Group master가 group member 한 명을 퇴출시킨다.")
    void releaseGroupMemberHttpRequest() throws Exception {
        UserIdDTO userIdDTO = new UserIdDTO();
        userIdDTO.setUserId(user1.getId());

        mockMvc.perform(post("/api/groups/{groupId}/release/members/{memberId}", group.getId(), user2.getId())
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userIdDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Group master가 아닌 유저가 다른 group member를 퇴출시키면 예외가 발생한다.")
    void NotGroupMasterReleaseGroupMemberHttpRequest() throws Exception {
        UserIdDTO userIdDTO = new UserIdDTO();
        userIdDTO.setUserId(user2.getId());

        mockMvc.perform(post("/api/groups/{groupId}/release/members/{memberId}", group.getId(), user1.getId())
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userIdDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("GROUP-003"))
                .andExpect(jsonPath("$.message").value("그룹장이 아닙니다."));
    }

    @Test
    @DisplayName("그룹의 상태를 조회한다.")
    void getGroupStatusHttpRequest() throws Exception {
        mockMvc.perform(get("/api/groups/{groupId}/status", group.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.groupId").value(group.getId()))
                .andExpect(jsonPath("$.groupStatus").value(group.getGroupStatus().toString()));
    }

    @Test
    @DisplayName("그룹의 상태를 변경한다.")
    void setGroupStatusHttpRequest() throws Exception {
        GroupStatusRequestDTO groupStatusRequestDTO = new GroupStatusRequestDTO();
        groupStatusRequestDTO.setGroupStatus(GroupStatus.END_GROUP);

        mockMvc.perform(post("/api/groups/{groupId}/status", group.getId())
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(groupStatusRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.groupId").value(group.getId()))
                .andExpect(jsonPath("$.groupStatus").value(GroupStatus.END_GROUP.toString()));

    }

    @Test
    @DisplayName("해당 그룹에 일대일 매핑되어있는 post id를 조회한다.")
    void getPostIdOfGroupHttpRequest() throws Exception {
        mockMvc.perform(get("/api/groups/{groupId}", group.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(post.getId()));
    }
}