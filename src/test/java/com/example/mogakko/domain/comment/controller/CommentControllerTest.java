package com.example.mogakko.domain.comment.controller;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.comment.dto.CommentRequestDTO;
import com.example.mogakko.domain.comment.dto.UpdateCommentDTO;
import com.example.mogakko.domain.comment.repository.CommentRepository;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.Project;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.user.controller.SessionConst;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    protected MockHttpSession session;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Post post;
    private Comment comment;

    @BeforeEach
    void setupDatabase() throws Exception {
        user = userRepository.save(createUser());
        post = postRepository.save(createPost());
        comment = commentRepository.save(createComment(post, user, null, "부모댓글1"));
        session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_USER, user.getId());
    }

    private User createUser() {
        User user = new User();
        user.setUsername("qwer1234");
        user.setPassword("qwer1234!");
        user.setNickname("담비");
        user.setOneLineIntroduction("안녕하세요.");
        user.setPhoneNumber("01012341234");
        user.setGithubAddress("qwer.github.com");
        user.setPicture("img");
        user.setAdmin(false);
        return user;
    }

    private Post createPost() {
        Project project = new Project();
        project.setUser(user);
        project.setTitle("제목");
        project.setContent("내용");
        return project;
    }

    private Comment createComment(Post post, User user, Comment root, String content) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setRoot(root);
        comment.setContent(content);
        return comment;
    }

    private CommentRequestDTO createCommentRequestDTO(Long rootCommentId) {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setPostId(post.getId());
        commentRequestDTO.setUserId(user.getId());
        commentRequestDTO.setContent("참여하고 싶습니다.");
        commentRequestDTO.setRootCommentId(rootCommentId);
        return commentRequestDTO;
    }

    @Test
    @DisplayName("부모댓글 추가 성공")
    void addCommentHttpRequest() throws Exception {
        CommentRequestDTO commentRequestDTO = createCommentRequestDTO(null);

        mockMvc.perform(post("/api/comments")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(post.getId()))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.content").value("참여하고 싶습니다."))
                .andExpect(jsonPath("$.rootCommentId").doesNotExist());
    }

    @Test
    @DisplayName("저장하려는 댓글의 부모댓글이 존재하지 않으면 예외가 발생한다.")
    void addCommentWithANonValidRootCommentHttpRequest() throws Exception {
        CommentRequestDTO commentRequestDTO = createCommentRequestDTO(-1L);

        mockMvc.perform(post("/api/comments")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("COMMENT-002"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 부모댓글입니다."));
    }

    @Test
    @DisplayName("저장하려는 댓글의 부모댓글이 해당 게시글에 속하지 않으면 예외가 발생한다.")
    void addCommentWithARootCommentNotBelongingToPostHttpRequest() throws Exception {
        Post anotherPost = postRepository.save(createPost());
        Comment anotherComment = commentRepository.save(createComment(anotherPost, user, null, "다른 게시글에 저장된 댓글"));

        CommentRequestDTO commentRequestDTO = createCommentRequestDTO(anotherComment.getId());

        mockMvc.perform(post("/api/comments")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("COMMENT-003"))
                .andExpect(jsonPath("$.message").value("해당 게시글에 속하는 부모댓글이 아닙니다."));
    }

    @Test
    @DisplayName("저장하려는 댓글의 부모댓글이 다른 댓글을 부모댓글로 가지면 예외가 발생한다.")
    void addCommentWithARootCommentHavingAnotherRootCommentHttpRequest() throws Exception {
        Comment hasAnotherRootComment = commentRepository.save(createComment(post, user, comment, "다른 댓글을 부모댓글로 갖는 댓글"));

        CommentRequestDTO commentRequestDTO = createCommentRequestDTO(hasAnotherRootComment.getId());

        mockMvc.perform(post("/api/comments")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDTO)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("COMMENT-004"))
                .andExpect(jsonPath("$.message").value("부모댓글이 다른 부모댓글을 가집니다."));
    }

    @Test
    @DisplayName("게시글의 모든 댓글을 조회한다.")
    void getCommentsOfPostHttpRequest() throws Exception {
        mockMvc.perform(get("/api/posts/{postId}/comments", post.getId())
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].content").value("부모댓글1"))
                .andExpect(jsonPath("$[0].rootCommentId").doesNotExist());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCommentHttpRequest() throws Exception {
        mockMvc.perform(delete("/api/comments/{commentId}", comment.getId())
                        .session(session))
                .andExpect(status().isOk());

        assertThat(commentRepository.findById(comment.getId()))
                .isEmpty();
    }

    @Test
    @DisplayName("댓글 수정")
    void updateCommentHttpRequest() throws Exception {
        UpdateCommentDTO updateCommentDTO = new UpdateCommentDTO();
        updateCommentDTO.setContent("수정된 내용");

        mockMvc.perform(patch("/api/comments/{commentId}", comment.getId())
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCommentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.postId").value(post.getId()))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.content").value("수정된 내용"))
                .andExpect(jsonPath("$.rootCommentId").doesNotExist());
    }
}