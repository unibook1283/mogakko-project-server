package com.example.mogakko.domain.comment.service;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.comment.dto.CommentRequestDTO;
import com.example.mogakko.domain.comment.dto.CommentResponseDTO;
import com.example.mogakko.domain.comment.repository.CommentRepository;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentResponseDTO saveComment(CommentRequestDTO commentRequestDTO) {
        Optional<Post> optionalPost = postRepository.findById(commentRequestDTO.getPostId());
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        Optional<User> optionalUser = userRepository.findById(commentRequestDTO.getUserId());
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        Comment rootComment = null;
        if (commentRequestDTO.getRootCommentId() != null) {
            Optional<Comment> optionalComment = commentRepository.findById(commentRequestDTO.getRootCommentId());
            rootComment = optionalComment.orElseThrow(() -> new IllegalArgumentException("잘못된 rootCommentId"));
            if (rootComment.getPost().getId() != post.getId())
                throw new IllegalArgumentException("rootComment가 해당 post의 댓글이 아닙니다.");
        }

        Comment comment = commentRequestDTO.toEntity(post, user, rootComment);

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDTO(saveComment);
    }

}
