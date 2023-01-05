package com.example.mogakko.domain.comment.repository;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
