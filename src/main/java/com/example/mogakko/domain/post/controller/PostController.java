package com.example.mogakko.domain.post.controller;

import com.example.mogakko.domain.post.dto.PostRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    @PostMapping("/posts")
    public PostRequestDTO test(@RequestBody PostRequestDTO postRequestDTO) {
        return postRequestDTO;
    }

}
