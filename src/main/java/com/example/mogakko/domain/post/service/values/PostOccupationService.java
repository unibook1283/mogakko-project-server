package com.example.mogakko.domain.post.service.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.values.PostOccupation;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.post.repository.values.PostOccupationRepository;
import com.example.mogakko.domain.values.domain.Occupation;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import com.example.mogakko.domain.values.repository.OccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostOccupationService {

    private final PostRepository postRepository;
    private final PostOccupationRepository postOccupationRepository;
    private final OccupationRepository occupationRepository;

    public void resetPostOccupation(Long postId) {    //게시글 수정에서 쓸 것
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        postOccupationRepository.deleteAllByPost(post);
    }

    public void saveOccupations(List<OccupationDTO> occupations, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        occupations.stream()
                .forEach(occupationDTO -> {
                    Optional<Occupation> optionalOccupation = occupationRepository.findById(occupationDTO.getOccupationId());
                    Occupation occupation = optionalOccupation.orElseThrow(() -> new IllegalArgumentException("잘못된 occupationId"));

                    PostOccupation postOccupation = PostOccupation.createPostOccupation(post, occupation);
                    postOccupationRepository.save(postOccupation);
                });
    }
}
