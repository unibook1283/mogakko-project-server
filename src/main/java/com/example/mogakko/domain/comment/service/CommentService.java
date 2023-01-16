package com.example.mogakko.domain.comment.service;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.comment.dto.CommentRequestDTO;
import com.example.mogakko.domain.comment.dto.CommentResponseDTO;
import com.example.mogakko.domain.comment.dto.UpdateCommentDTO;
import com.example.mogakko.domain.comment.exception.CommentNotFoundException;
import com.example.mogakko.domain.comment.exception.RootCommentHasAnotherRootCommentException;
import com.example.mogakko.domain.comment.exception.RootCommentNotBelongToPostException;
import com.example.mogakko.domain.comment.exception.RootCommentNotFoundException;
import com.example.mogakko.domain.comment.repository.CommentRepository;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.exception.PostNotFoundException;
import com.example.mogakko.domain.post.repository.PostRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDTO saveComment(CommentRequestDTO commentRequestDTO) {
        Post post = postRepository.findById(commentRequestDTO.getPostId())
                .orElseThrow(PostNotFoundException::new);

        User user = userRepository.findById(commentRequestDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Comment rootComment = null;
        if (commentRequestDTO.getRootCommentId() != null) {
            Optional<Comment> optionalComment = commentRepository.findById(commentRequestDTO.getRootCommentId());
            rootComment = optionalComment.orElseThrow(RootCommentNotFoundException::new);
            if (rootComment.getPost().getId() != post.getId())
                throw new RootCommentNotBelongToPostException();
            if (rootComment.getRoot() != null)
                throw new RootCommentHasAnotherRootCommentException();
        }

        Comment comment = commentRequestDTO.toEntity(post, user, rootComment);

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDTO(saveComment);
    }

    public List<CommentResponseDTO> findCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return commentRepository.findByPost(post).stream()
                .map(comment -> new CommentResponseDTO(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        commentRepository.deleteAllByRoot(comment);
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public CommentResponseDTO updateComment(Long commentId, UpdateCommentDTO updateCommentDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        comment.setContent(updateCommentDTO.getContent());
        return new CommentResponseDTO(comment);
    }
}
