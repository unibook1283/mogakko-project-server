package com.example.mogakko.domain.report.service;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.report.domain.PostReport;
import com.example.mogakko.domain.report.dto.PostReportDTO;
import com.example.mogakko.domain.report.dto.PostReportRequestDTO;
import com.example.mogakko.domain.report.repository.PostReportRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostReportDTO savePostReport(PostReportRequestDTO postReportRequestDTO) {
        Optional<Post> optionalPost = postRepository.findById(postReportRequestDTO.getPostId());
        Post post = optionalPost.orElseThrow(() -> new IllegalArgumentException("잘못된 postId"));

        Optional<User> optionalUser = userRepository.findById(postReportRequestDTO.getUserId());
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        PostReport postReport = new PostReport();
        postReport.setPost(post);
        postReport.setUser(user);
        postReport.setContent(postReportRequestDTO.getContent());

        PostReport savePostReport = postReportRepository.save(postReport);
        return new PostReportDTO(savePostReport);
    }
}
