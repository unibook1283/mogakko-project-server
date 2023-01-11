package com.example.mogakko.domain.report.controller;

import com.example.mogakko.domain.report.dto.PostReportDTO;
import com.example.mogakko.domain.report.dto.PostReportRequestDTO;
import com.example.mogakko.domain.report.service.PostReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostReportController {

    private final PostReportService postReportService;

    @PostMapping("/post-reports")
    public PostReportDTO addPostReport(@RequestBody PostReportRequestDTO postReportRequestDTO) {
        return postReportService.savePostReport(postReportRequestDTO);
    }

    @GetMapping("/posts/{postId}/post-reports")
    public List<PostReportDTO> getPostReport(@PathVariable Long postId) {
        return postReportService.findPostReports(postId);
    }

}
