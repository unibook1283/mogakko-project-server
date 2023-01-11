package com.example.mogakko.domain.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentReportRequestDTO {

    private Long commentId;

    private Long userId;

    private String content;

}
