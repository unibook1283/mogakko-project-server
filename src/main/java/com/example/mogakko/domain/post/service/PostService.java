package com.example.mogakko.domain.post.service;

import com.example.mogakko.domain.post.domain.Post;
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

        return new PostResponseDTO(postRequestDTO, savePost.getId(), user);
    }

}
