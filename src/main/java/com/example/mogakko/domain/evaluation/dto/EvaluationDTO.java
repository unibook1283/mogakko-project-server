package com.example.mogakko.domain.evaluation.dto;

import com.example.mogakko.domain.evaluation.domain.Evaluation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EvaluationDTO {

    private Long evaluationId;

    private Long evaluatedUserId;

    private Long evaluatingUserId;

    private String evaluatingUserNickname;

    private String content;

    public EvaluationDTO(Evaluation evaluation) {
        this.evaluationId = evaluation.getId();
        this.evaluatedUserId = evaluation.getEvaluatedUser().getId();
        this.evaluatingUserId = evaluation.getEvaluatingUser().getId();
        this.evaluatingUserNickname = evaluation.getEvaluatingUser().getNickname();
        this.content = evaluation.getContent();
    }

}
