package com.example.mogakko.domain.comment.dto;

import com.example.mogakko.domain.comment.domain.Comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDTO {

    private Long commentId;

    private Long postId;

    private Long userId;

    private String nickname;

    private String content;

    private Long rootCommentId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.userId = comment.getUser().getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        if (comment.getRoot() != null)
            this.rootCommentId = comment.getRoot().getId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
