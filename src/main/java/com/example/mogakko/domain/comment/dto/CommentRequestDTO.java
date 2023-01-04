package com.example.mogakko.domain.comment.dto;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDTO {

    private Long postId;

    private Long userId;

    private String content;

    private Long rootCommentId;

    public Comment toEntity(Post post, User user, Comment rootComment) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setRoot(rootComment);
        comment.setContent(content);
        return comment;
    }
}
