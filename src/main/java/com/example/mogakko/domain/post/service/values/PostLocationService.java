package com.example.mogakko.domain.post.service.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.values.PostLocation;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.post.repository.values.PostLocationRepository;
import com.example.mogakko.domain.values.domain.Location;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLocationService {

    private final PostRepository postRepository;
    private final PostLocationRepository postLocationRepository;
    private final LocationRepository locationRepository;

    public void resetPostLocation(Long postId) {    //게시글 수정에서 쓸 것
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        postLocationRepository.deleteAllByPost(post);
    }

    public List<LocationDTO> saveLocations(List<LocationDTO> locations, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        return locations.stream()
                .map(locationDTO -> {
                    Optional<Location> optionalLocation = locationRepository.findById(locationDTO.getLocationId());
                    Location location = optionalLocation.orElseThrow(() -> new IllegalArgumentException("잘못된 locationId"));

                    PostLocation postLocation = PostLocation.createPostLocation(post, location);
                    postLocationRepository.save(postLocation);
                    return new LocationDTO(location);
                }).collect(Collectors.toList());
    }
}
