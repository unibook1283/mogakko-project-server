package com.example.mogakko.domain.report.dto;

import com.example.mogakko.domain.report.domain.PostReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostReportDTO {

    private Long postReportId;

    private Long postId;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;

    public PostReportDTO(PostReport postReport) {
        this.postReportId = postReport.getId();
        this.postId = postReport.getPost().getId();
        this.userId = postReport.getUser().getId();
        this.content = postReport.getContent();
        this.createdAt = postReport.getCreatedAt();
    }
}
