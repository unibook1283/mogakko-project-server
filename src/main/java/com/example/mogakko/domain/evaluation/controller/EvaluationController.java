package com.example.mogakko.domain.evaluation.controller;

import com.example.mogakko.domain.evaluation.dto.AddEvaluationRequestDTO;
import com.example.mogakko.domain.evaluation.dto.EvaluationDTO;
import com.example.mogakko.domain.evaluation.repository.EvaluationRepository;
import com.example.mogakko.domain.evaluation.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @PostMapping("/users/{userId}/evaluations")
    public EvaluationDTO addEvaluation(@PathVariable Long userId, @RequestBody AddEvaluationRequestDTO addEvaluationRequestDTO) {
        return evaluationService.saveEvaluation(userId, addEvaluationRequestDTO);
    }

    @GetMapping("/users/{userId}/evaluations")
    public List<EvaluationDTO> getEvaluation(@PathVariable Long userId) {
        return evaluationService.findEvaluationsOfUser(userId);
    }

    @DeleteMapping("/evaluations/{evaluationId}")
    public void deleteEvaluation(@PathVariable Long evaluationId) {
        evaluationService.deleteById(evaluationId);
    }
}
