package com.example.mogakko.domain.post.service.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.values.PostLocation;
import com.example.mogakko.domain.post.exception.PostNotFoundException;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.post.repository.values.PostLocationRepository;
import com.example.mogakko.domain.values.domain.Location;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.exception.LocationNotFoundException;
import com.example.mogakko.domain.values.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostLocationService {

    private final PostRepository postRepository;
    private final PostLocationRepository postLocationRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public void resetPostLocation(Long postId) {    //게시글 수정에서 쓸 것
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postLocationRepository.deleteAllByPost(post);
    }

    @Transactional
    public List<LocationDTO> saveLocations(List<LocationDTO> locations, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return locations.stream()
                .map(locationDTO -> {
                    Location location = locationRepository.findById(locationDTO.getLocationId())
                            .orElseThrow(LocationNotFoundException::new);

                    PostLocation postLocation = PostLocation.createPostLocation(post, location);
                    postLocationRepository.save(postLocation);
                    return new LocationDTO(location);
                }).collect(Collectors.toList());
    }
}
