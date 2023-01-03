package com.example.mogakko.domain.post.repository.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.values.PostOccupation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostOccupationRepository extends JpaRepository<PostOccupation, Long> {
    List<PostOccupation> findByPost(Post post);
}
