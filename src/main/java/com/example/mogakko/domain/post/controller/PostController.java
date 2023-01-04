package com.example.mogakko.domain.post.controller;

import com.example.mogakko.domain.post.dto.PostRequestDTO;
import com.example.mogakko.domain.post.dto.PostResponseDTO;
import com.example.mogakko.domain.post.service.PostService;
import com.example.mogakko.domain.post.service.values.PostLanguageService;
import com.example.mogakko.domain.post.service.values.PostLocationService;
import com.example.mogakko.domain.post.service.values.PostOccupationService;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostLanguageService postLanguageService;
    private final PostLocationService postLocationService;
    private final PostOccupationService postOccupationService;

    @PostMapping("/posts")
    public PostResponseDTO addPost(@RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.savePost(postRequestDTO);
        Long postId = postResponseDTO.getPostId();

        List<LanguageDTO> languages = postLanguageService.saveLanguages(postRequestDTO.getLanguages(), postId);
        postResponseDTO.setLanguages(languages);
        List<LocationDTO> locations = postLocationService.saveLocations(postRequestDTO.getLocations(), postId);
        postResponseDTO.setLocations(locations);
        List<OccupationDTO> occupations = postOccupationService.saveOccupations(postRequestDTO.getOccupations(), postId);
        postResponseDTO.setOccupations(occupations);

        return postResponseDTO;
    }

    @PostMapping("/posts/{postId}")
    public PostResponseDTO updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = postService.updatePost(postId, postRequestDTO);

        postLanguageService.resetPostLanguage(postId);
        List<LanguageDTO> languages = postLanguageService.saveLanguages(postRequestDTO.getLanguages(), postId);
        postResponseDTO.setLanguages(languages);

        postLocationService.resetPostLocation(postId);
        List<LocationDTO> locations = postLocationService.saveLocations(postRequestDTO.getLocations(), postId);
        postResponseDTO.setLocations(locations);

        postOccupationService.resetPostOccupation(postId);
        List<OccupationDTO> occupations = postOccupationService.saveOccupations(postRequestDTO.getOccupations(), postId);
        postResponseDTO.setOccupations(occupations);

        return postResponseDTO;
    }

    @GetMapping("/posts/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId) {
        return postService.findOne(postId);
    }

}
