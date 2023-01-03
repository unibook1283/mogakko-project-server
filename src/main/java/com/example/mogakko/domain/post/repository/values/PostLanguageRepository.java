package com.example.mogakko.domain.post.repository.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.values.PostLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLanguageRepository extends JpaRepository<PostLanguage, Long> {
    List<PostLanguage> findByPost(Post post);
}
