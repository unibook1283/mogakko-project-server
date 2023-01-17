package com.example.mogakko.domain.comment.service;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.comment.dto.CommentRequestDTO;
import com.example.mogakko.domain.comment.dto.CommentResponseDTO;
import com.example.mogakko.domain.comment.dto.UpdateCommentDTO;
import com.example.mogakko.domain.comment.exception.RootCommentHasAnotherRootCommentException;
import com.example.mogakko.domain.comment.exception.RootCommentNotBelongToPostException;
import com.example.mogakko.domain.comment.exception.RootCommentNotFoundException;
import com.example.mogakko.domain.comment.repository.CommentRepository;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.Project;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

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
    void setupDatabase() {
        user = userRepository.save(createUser());
        post = postRepository.save(createPost());
        comment = commentRepository.save(createComment(post, user, null, "부모댓글1"));
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

    @Test
    @DisplayName("댓글 하나와, 그 댓글을 부모댓글로 갖는 댓글 하나를 생성한다.")
    void saveCommentTest() {
        CommentRequestDTO 부모댓글2 = createCommentRequestDTO("부모댓글2", null);
        CommentResponseDTO rootCommentDTO = commentService.saveComment(부모댓글2);
        CommentRequestDTO 자식댓글 = createCommentRequestDTO("자식댓글", rootCommentDTO.getCommentId());
        CommentResponseDTO childCommentDTO = commentService.saveComment(자식댓글);

        Optional<Comment> optionalRootComment = commentRepository.findById(rootCommentDTO.getCommentId());
        assertThat(optionalRootComment).as("저장한 부모댓글이 존재해야한다.")
                .isNotEmpty();
        Comment rootComment = optionalRootComment.get();

        Optional<Comment> optionalChildComment = commentRepository.findById(childCommentDTO.getCommentId());
        assertThat(optionalChildComment).as("저장한 자식댓글이 존재해야한다.")
                .isNotEmpty();
        Comment childComment = optionalChildComment.get();

        assertThat(childComment.getRoot())
                .as("부모댓글은 자식댓글의 root이어야 한다.")
                .isEqualTo(rootComment);
    }

    @Test
    @DisplayName("저장하려는 댓글의 부모댓글이 존재하지 않으면 예외가 발생한다.")
    void saveCommentRootCommentNotFoundTest() {
        CommentRequestDTO 자식댓글 = createCommentRequestDTO("자식댓글", -1L);

        assertThatThrownBy(() -> commentService.saveComment(자식댓글))
                .isInstanceOf(RootCommentNotFoundException.class);
    }

    @Test
    @DisplayName("저장하려는 댓글의 부모댓글이 해당 게시글에 속하지 않으면 예외가 발생한다.")
    void saveCommentRootCommentNotBelongToPostTest() {
        Post anotherPost = postRepository.save(createPost());
        Comment anotherComment = commentRepository.save(createComment(anotherPost, user, null, "다른 게시글에 저장된 댓글"));

        CommentRequestDTO 자식댓글 = createCommentRequestDTO("자식댓글", anotherComment.getId());
        assertThatThrownBy(() -> commentService.saveComment(자식댓글))
                .isInstanceOf(RootCommentNotBelongToPostException.class);
    }

    @Test
    @DisplayName("저장하려는 댓글의 부모댓글이 다른 댓글을 부모댓글로 가지면 예외가 발생한다.")
    void saveCommentRootCommentHasAnotherRootCommentTest() {
        Comment hasAnotherRootComment = commentRepository.save(createComment(post, user, comment, "다른 댓글을 부모댓글로 갖는 댓글"));

        CommentRequestDTO 자식댓글 = createCommentRequestDTO("자식댓글", hasAnotherRootComment.getId());
        assertThatThrownBy(() -> commentService.saveComment(자식댓글))
                .isInstanceOf(RootCommentHasAnotherRootCommentException.class);
    }

    private CommentRequestDTO createCommentRequestDTO(String content, Long rootCommentId) {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setPostId(post.getId());
        commentRequestDTO.setUserId(user.getId());
        commentRequestDTO.setContent(content);
        commentRequestDTO.setRootCommentId(rootCommentId);
        return commentRequestDTO;
    }

    @Test
    @DisplayName("게시글의 모든 댓글을 조회한다.")
    void findCommentsByPostTest() {
        List<CommentResponseDTO> comments = commentService.findCommentsByPost(post.getId());

        assertThat(comments).as("이 게시글은 1개의 댓글을 가져야 한다.")
                .hasSize(1);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCommentTest() {
        commentService.deleteComment(comment.getId());

        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());

        assertThat(optionalComment.isEmpty()).as("삭제한 댓글을 조회하면 null이어야 한다.")
                .isTrue();
    }

    @Test
    @DisplayName("댓글 수정")
    void updateCommentTest() {
        final String updateContent = "수정된 내용";
        UpdateCommentDTO updateCommentDTO = new UpdateCommentDTO();
        updateCommentDTO.setContent(updateContent);
        commentService.updateComment(comment.getId(), updateCommentDTO);

        Optional<Comment> optionalComment = commentRepository.findById(comment.getId());
        assertThat(optionalComment).as("댓글이 존재해야 한다.")
                .isNotEmpty();
        Comment updatedComment = optionalComment.get();

        assertThat(updatedComment.getContent()).as("댓글의 내용이 수정되어 있어야 한다.")
                .isEqualTo(updateContent);
    }

}