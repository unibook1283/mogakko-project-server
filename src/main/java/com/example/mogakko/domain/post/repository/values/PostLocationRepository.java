package com.example.mogakko.domain.post.repository.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.values.PostLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLocationRepository extends JpaRepository<PostLocation, Long> {
    List<PostLocation> findByPost(Post post);
    void deleteAllByPost(Post post);

}
