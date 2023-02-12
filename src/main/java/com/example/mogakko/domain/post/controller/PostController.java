package com.example.mogakko.domain.post.controller;

import com.example.mogakko.domain.post.dto.PostRequestDTO;
import com.example.mogakko.domain.post.dto.PostResponseDTO;
import com.example.mogakko.domain.post.enums.Type;
import com.example.mogakko.domain.post.service.PostService;
import com.example.mogakko.domain.post.service.values.PostLanguageService;
import com.example.mogakko.domain.post.service.values.PostLocationService;
import com.example.mogakko.domain.post.service.values.PostOccupationService;
import com.example.mogakko.domain.user.dto.UserDTO;
import com.example.mogakko.domain.user.service.UserService;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public PostResponseDTO addPost(@RequestBody PostRequestDTO postRequestDTO) {
        return postService.savePost(postRequestDTO);
    }

    @PostMapping("/posts/{postId}")
    public PostResponseDTO updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO) {
        return postService.updatePost(postId, postRequestDTO);
    }

    @GetMapping("/posts/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId) {
        return postService.findOne(postId);
    }

    @GetMapping("/users/{userId}/posts")
    public List<PostResponseDTO> getPostOfUser(@PathVariable Long userId) {
        return postService.findPostsByUser(userId);
    }

    @GetMapping("/posts/type/{postType}")
    public List<PostResponseDTO> getPostOfType(@PathVariable String postType) {
        return postService.findPostsByType(postType);
    }

    @GetMapping("/groups/{groupId}/posts/type/study")
    public List<PostResponseDTO> getStudyPostsOfGroup(@PathVariable Long groupId) {
        return postService.findStudyPostsByGroupId(groupId);
    }

}
