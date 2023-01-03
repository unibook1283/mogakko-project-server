package com.example.mogakko.domain.post.service.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.values.PostLanguage;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.post.repository.values.PostLanguageRepository;
import com.example.mogakko.domain.values.domain.Language;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLanguageService {

    private final PostRepository postRepository;
    private final PostLanguageRepository postLanguageRepository;
    private final LanguageRepository languageRepository;

    public void resetPostLanguage(Long postId) {    //게시글 수정에서 쓸 것
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        postLanguageRepository.deleteAllByPost(post);
    }

    public void saveLanguages(List<LanguageDTO> languages, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        languages.stream()
                        .forEach(languageDTO -> {
                            Optional<Language> optionalLanguage = languageRepository.findById(languageDTO.getLanguageId());
                            Language language = optionalLanguage.orElseThrow(() -> new IllegalArgumentException("잘못된 languageId"));

                            PostLanguage postLanguage = PostLanguage.createPostLanguage(post, language);
                            postLanguageRepository.save(postLanguage);
                        });
    }
}
