package com.example.mogakko.domain.report.service;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.exception.PostNotFoundException;
import com.example.mogakko.domain.post.repository.PostRepository;
import com.example.mogakko.domain.report.domain.PostReport;
import com.example.mogakko.domain.report.dto.PostReportDTO;
import com.example.mogakko.domain.report.dto.PostReportRequestDTO;
import com.example.mogakko.domain.report.repository.PostReportRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostReportService {

    private final PostReportRepository postReportRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostReportDTO savePostReport(PostReportRequestDTO postReportRequestDTO) {
        Post post = postRepository.findById(postReportRequestDTO.getPostId())
                .orElseThrow(PostNotFoundException::new);

        User user = userRepository.findById(postReportRequestDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);

        PostReport postReport = new PostReport();
        postReport.setPost(post);
        postReport.setUser(user);
        postReport.setContent(postReportRequestDTO.getContent());

        PostReport savePostReport = postReportRepository.save(postReport);
        return new PostReportDTO(savePostReport);
    }

    public List<PostReportDTO> findPostReports(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        List<PostReport> postReports = postReportRepository.findByPost(post);

        return postReports.stream()
                .map(postReport -> new PostReportDTO(postReport))
                .collect(Collectors.toList());
    }
}
