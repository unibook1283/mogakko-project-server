package com.example.mogakko.domain.post.repository;

import com.example.mogakko.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByDtype(String dtype);
}
