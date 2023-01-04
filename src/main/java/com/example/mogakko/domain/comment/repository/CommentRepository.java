package com.example.mogakko.domain.comment.repository;

import com.example.mogakko.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
