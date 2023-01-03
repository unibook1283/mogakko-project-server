package com.example.mogakko.domain.post.repository;

import com.example.mogakko.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
