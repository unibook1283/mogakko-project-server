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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLanguageService {

    private final PostRepository postRepository;
    private final PostLanguageRepository postLanguageRepository;
    private final LanguageRepository languageRepository;

    public void resetPostLanguage(Long postId) {    //게시글 수정에서 쓸 것
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        postLanguageRepository.deleteAllByPost(post);
    }

    public List<LanguageDTO> saveLanguages(List<LanguageDTO> languages, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        return languages.stream()
                .map(languageDTO -> {
                    Language language = languageRepository.findById(languageDTO.getLanguageId())
                            .orElseThrow(() -> new IllegalArgumentException("잘못된 languageId"));

                    PostLanguage postLanguage = PostLanguage.createPostLanguage(post, language);
                    postLanguageRepository.save(postLanguage);
                    return new LanguageDTO(language);
                }).collect(Collectors.toList());
    }
}
