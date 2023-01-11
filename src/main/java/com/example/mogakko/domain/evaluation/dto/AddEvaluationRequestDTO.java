package com.example.mogakko.domain.evaluation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddEvaluationRequestDTO {

    private Long evaluatingUserId;

    private String content;

}
