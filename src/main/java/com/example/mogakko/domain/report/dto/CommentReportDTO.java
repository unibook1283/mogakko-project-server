package com.example.mogakko.domain.report.dto;

import com.example.mogakko.domain.report.domain.CommentReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentReportDTO {

    private Long commentReportId;

    private Long commentId;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;

    public CommentReportDTO(CommentReport commentReport) {
        this.commentReportId = commentReport.getId();
        this.commentId = commentReport.getComment().getId();
        this.userId = commentReport.getUser().getId();
        this.content = commentReport.getContent();
        this.createdAt = commentReport.getCreatedAt();
    }
}
