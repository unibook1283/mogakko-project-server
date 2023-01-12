package com.example.mogakko.domain.comment.service;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.comment.dto.CommentRequestDTO;
import com.example.mogakko.domain.comment.dto.CommentResponseDTO;
import com.example.mogakko.domain.comment.dto.UpdateCommentDTO;
import com.example.mogakko.domain.comment.repository.CommentRepository;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.repository.PostRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponseDTO saveComment(CommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(commentRequestDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        User user = userRepository.findById(commentRequestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        Comment rootComment = null;
        if (commentRequestDTO.getRootCommentId() != null) {
            Optional<Comment> optionalComment = commentRepository.findById(commentRequestDTO.getRootCommentId());
            rootComment = optionalComment.orElseThrow(() -> new IllegalArgumentException("잘못된 rootCommentId"));
            if (rootComment.getPost().getId() != post.getId())
                throw new IllegalArgumentException("rootComment가 해당 post의 댓글이 아닙니다.");
            if (rootComment.getRoot() != null)
                throw new IllegalArgumentException("rootComment는 다른 rootComment를 갖지 않아야 합니다.");
        }

        Comment comment = commentRequestDTO.toEntity(post, user, rootComment);

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDTO(saveComment);
    }

    public List<CommentResponseDTO> findCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        return commentRepository.findByPost(post).stream()
                .map(comment -> new CommentResponseDTO(comment))
                .collect(Collectors.toList());
    }

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 commentId"));
        commentRepository.deleteAllByRoot(comment);
        commentRepository.deleteById(commentId);
    }

    public CommentResponseDTO updateComment(Long commentId, UpdateCommentDTO updateCommentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 commentId"));

        comment.setContent(updateCommentDTO.getContent());
        return new CommentResponseDTO(comment);
    }
}
