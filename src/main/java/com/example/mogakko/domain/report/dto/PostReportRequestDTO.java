package com.example.mogakko.domain.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReportRequestDTO {

    private Long postId;

    private Long userId;

    private String content;

}
