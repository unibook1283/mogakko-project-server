package com.example.mogakko.domain.evaluation.service;

import com.example.mogakko.domain.evaluation.domain.Evaluation;
import com.example.mogakko.domain.evaluation.dto.AddEvaluationRequestDTO;
import com.example.mogakko.domain.evaluation.dto.ContentDTO;
import com.example.mogakko.domain.evaluation.dto.EvaluationDTO;
import com.example.mogakko.domain.evaluation.exception.EvaluatedUserNotFoundException;
import com.example.mogakko.domain.evaluation.exception.EvaluatingUserNotFoundException;
import com.example.mogakko.domain.evaluation.exception.EvaluationNotFoundException;
import com.example.mogakko.domain.evaluation.repository.EvaluationRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final UserRepository userRepository;

    @Transactional
    public EvaluationDTO saveEvaluation(Long evaluatedUserId, AddEvaluationRequestDTO addEvaluationRequestDTO) {
        User evaluated = userRepository.findById(evaluatedUserId)
                .orElseThrow(EvaluatedUserNotFoundException::new);

        User evaluating = userRepository.findById(addEvaluationRequestDTO.getEvaluatingUserId())
                .orElseThrow(EvaluatingUserNotFoundException::new);

        Evaluation evaluation = new Evaluation();
        evaluation.setEvaluatedUser(evaluated);
        evaluation.setEvaluatingUser(evaluating);
        evaluation.setContent(addEvaluationRequestDTO.getContent());

        Evaluation saveEvaluation = evaluationRepository.save(evaluation);

        return new EvaluationDTO(saveEvaluation);
    }

    public List<EvaluationDTO> findEvaluationsOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<Evaluation> evaluations = evaluationRepository.findByEvaluatedUser(user);
        return evaluations.stream()
                .map(evaluation -> new EvaluationDTO(evaluation))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Long evaluationId) {
        evaluationRepository.deleteById(evaluationId);
    }

    public EvaluationDTO updateEvaluation(Long evaluationId, ContentDTO contentDTO) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(EvaluationNotFoundException::new);

        evaluation.setContent(contentDTO.getContent());

        return new EvaluationDTO(evaluation);
    }
}
