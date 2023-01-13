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
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostOccupationService {

    private final PostRepository postRepository;
    private final PostOccupationRepository postOccupationRepository;
    private final OccupationRepository occupationRepository;

    @Transactional
    public void resetPostOccupation(Long postId) {    //게시글 수정에서 쓸 것
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        postOccupationRepository.deleteAllByPost(post);
    }

    @Transactional
    public List<OccupationDTO> saveOccupations(List<OccupationDTO> occupations, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        return occupations.stream()
                .map(occupationDTO -> {
                    Occupation occupation = occupationRepository.findById(occupationDTO.getOccupationId())
                            .orElseThrow(() -> new IllegalArgumentException("잘못된 occupationId"));

                    PostOccupation postOccupation = PostOccupation.createPostOccupation(post, occupation);
                    postOccupationRepository.save(postOccupation);
                    return new OccupationDTO(occupation);
                }).collect(Collectors.toList());
    }
}
