package com.example.mogakko.domain.post.service;

import com.example.mogakko.domain.post.domain.*;
import com.example.mogakko.domain.post.domain.enums.Type;
import com.example.mogakko.domain.post.dto.PostRequestDTO;
import com.example.mogakko.domain.post.dto.PostResponseDTO;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDTO savePost(PostRequestDTO postRequestDTO) {    // languages, locations, occupations는 여기서 말고 values service에서

        Optional<User> userOptional = userRepository.findById(postRequestDTO.getUserId());
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        Post post = postRequestDTO.toEntity(user);
        Post savePost = postRepository.save(post);

        return new PostResponseDTO(post);
    }

    public PostResponseDTO updatePost(Long postId, PostRequestDTO postRequestDTO) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        Optional<User> userOptional = userRepository.findById(postRequestDTO.getUserId());
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        post.setTitle(postRequestDTO.getTitle());
        post.setContent(postRequestDTO.getContent());
        setSpecificInfo(postRequestDTO, post);

        return new PostResponseDTO(post);
    }

    private void setSpecificInfo(PostRequestDTO postRequestDTO, Post post) {
        Type type = postRequestDTO.getType();
        switch (type) {
            case PROJECT:
                Project project = (Project) post;
                project.setDeadline(postRequestDTO.getDeadline());
                return;
            case MOGAKKO:
                Mogakko mogakko = (Mogakko) post;
                mogakko.setDeadline(postRequestDTO.getDeadline());
                mogakko.setTerm(postRequestDTO.getTerm());
                return;
            case QUESTION:
                Question question = (Question) post;
                return;
            case STUDY:
                Study study = (Study) post;
                return;
        }
    }

    public PostResponseDTO findOne(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        PostResponseDTO postResponseDTO = new PostResponseDTO(post);

        return postResponseDTO;
    }

}
