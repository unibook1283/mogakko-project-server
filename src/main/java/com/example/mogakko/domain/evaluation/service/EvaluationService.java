package com.example.mogakko.domain.evaluation.service;

import com.example.mogakko.domain.evaluation.domain.Evaluation;
import com.example.mogakko.domain.evaluation.dto.AddEvaluationRequestDTO;
import com.example.mogakko.domain.evaluation.dto.ContentDTO;
import com.example.mogakko.domain.evaluation.dto.EvaluationDTO;
import com.example.mogakko.domain.evaluation.repository.EvaluationRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final UserRepository userRepository;

    public EvaluationDTO saveEvaluation(Long evaluatedUserId, AddEvaluationRequestDTO addEvaluationRequestDTO) {
        Optional<User> optionalEvaluated = userRepository.findById(evaluatedUserId);
        User evaluated = optionalEvaluated.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        Optional<User> optionalEvaluating = userRepository.findById(addEvaluationRequestDTO.getEvaluatingUserId());
        User evaluating = optionalEvaluating.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluatedUser(evaluated);
        evaluation.setEvaluatingUser(evaluating);
        evaluation.setContent(addEvaluationRequestDTO.getContent());

        Evaluation saveEvaluation = evaluationRepository.save(evaluation);

        return new EvaluationDTO(saveEvaluation);
    }

    public List<EvaluationDTO> findEvaluationsOfUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        List<Evaluation> evaluations = evaluationRepository.findByEvaluatedUser(user);
        return evaluations.stream()
                .map(evaluation -> new EvaluationDTO(evaluation))
                .collect(Collectors.toList());
    }

    public void deleteById(Long evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }

    public EvaluationDTO updateEvaluation(Long evaluationId, ContentDTO contentDTO) {
        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluationId);
        Evaluation evaluation = optionalEvaluation.orElseThrow(() -> new IllegalArgumentException("잘못된 evaluationId"));

        evaluation.setContent(contentDTO.getContent());

        return new EvaluationDTO(evaluation);
    }
}
