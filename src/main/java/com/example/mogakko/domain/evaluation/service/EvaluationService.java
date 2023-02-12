package com.example.mogakko.domain.evaluation.service;

import com.example.mogakko.domain.evaluation.domain.Evaluation;
import com.example.mogakko.domain.evaluation.dto.AddEvaluationRequestDTO;
import com.example.mogakko.domain.evaluation.dto.ContentDTO;
import com.example.mogakko.domain.evaluation.dto.EvaluationDTO;
import com.example.mogakko.domain.evaluation.exception.*;
import com.example.mogakko.domain.evaluation.repository.EvaluationRepository;
import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.group.exception.GroupNotFoundException;
import com.example.mogakko.domain.group.repository.GroupRepository;
import com.example.mogakko.domain.group.repository.GroupUserRepository;
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
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupUserRepository groupUserRepository;

    @Transactional
    public EvaluationDTO saveEvaluation(Long groupId, Long evaluatedUserId, AddEvaluationRequestDTO addEvaluationRequestDTO) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        User evaluatedUser = userRepository.findById(evaluatedUserId)
                .orElseThrow(EvaluatedUserNotFoundException::new);

        User evaluatingUser = userRepository.findById(addEvaluationRequestDTO.getEvaluatingUserId())
                .orElseThrow(EvaluatingUserNotFoundException::new);

        groupUserRepository.findByGroupAndUser(group, evaluatedUser)
                .orElseThrow(EvaluatedUserNotBelongToGroupException::new);

        groupUserRepository.findByGroupAndUser(group, evaluatingUser)
                .orElseThrow(EvaluatingUserNotBelongToGroupException::new);

        if (evaluationRepository.findByGroupAndEvaluatedUserAndEvaluatingUser(group, evaluatedUser, evaluatingUser).isPresent()) {
            throw new AlreadyEvaluatedException();
        }

        Evaluation evaluation = new Evaluation();
        evaluation.setGroup(group);
        evaluation.setEvaluatedUser(evaluatedUser);
        evaluation.setEvaluatingUser(evaluatingUser);
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
