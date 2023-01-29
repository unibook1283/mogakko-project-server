package com.example.mogakko.domain.report.controller;

import com.example.mogakko.domain.report.dto.CommentReportDTO;
import com.example.mogakko.domain.report.dto.CommentReportRequestDTO;
import com.example.mogakko.domain.report.service.CommentReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentReportController {

    private final CommentReportService commentReportService;

    @PostMapping("/comment-reports")
    public CommentReportDTO addCommentReport(@RequestBody CommentReportRequestDTO commentReportRequestDTO) {
        return commentReportService.saveCommentReport(commentReportRequestDTO);
    }

    @GetMapping("/comments/{commentId}/comment-reports")
    public List<CommentReportDTO> getCommentReport(@PathVariable Long commentId) {
        return commentReportService.findCommentReports(commentId);
    }

}
