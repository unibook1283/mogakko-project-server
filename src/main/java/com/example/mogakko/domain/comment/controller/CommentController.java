package com.example.mogakko.domain.comment.controller;

import com.example.mogakko.domain.comment.dto.CommentRequestDTO;
import com.example.mogakko.domain.comment.dto.CommentResponseDTO;
import com.example.mogakko.domain.comment.service.CommentService;
import com.example.mogakko.domain.post.dto.PostResponseDTO;
import com.example.mogakko.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/comments")
    public CommentResponseDTO addComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        return commentService.saveComment(commentRequestDTO);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponseDTO> getCommentsOfPost(@PathVariable Long postId) {
        return commentService.findCommentsByPost(postId);
    }
}
