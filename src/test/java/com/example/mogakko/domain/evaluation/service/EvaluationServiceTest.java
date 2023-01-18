package com.example.mogakko.domain.evaluation.service;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.evaluation.domain.Evaluation;
import com.example.mogakko.domain.evaluation.dto.AddEvaluationRequestDTO;
import com.example.mogakko.domain.evaluation.dto.ContentDTO;
import com.example.mogakko.domain.evaluation.dto.EvaluationDTO;
import com.example.mogakko.domain.evaluation.repository.EvaluationRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@Transactional
class EvaluationServiceTest {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UserRepository userRepository;

    private User user1, user2;
    private Evaluation evaluation;

    @BeforeEach
    void setupDatabase() {
        user1 = userRepository.save(createUser("qwer1234", "qwer1234!", "담비", "안녕하세요.", "01012341234", "qwer.github.com", "img1"));
        user2 = userRepository.save(createUser("asdf1234", "asdf1234!", "단비", "안녕하세요.", "01012341235", "asdf.github.com", "img2"));
        evaluation = evaluationRepository.save(createEvaluation(user1, user2, "user2가 user1을 평가"));
    }

    private User createUser(String username, String password, String nickname, String oneLineIntroduction, String phoneNumber, String githubAddress, String picture) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setOneLineIntroduction(oneLineIntroduction);
        user.setPhoneNumber(phoneNumber);
        user.setGithubAddress(githubAddress);
        user.setPicture(picture);
        user.setAdmin(false);
        return user;
    }

    private Evaluation createEvaluation(User evaluatedUser, User evaluatingUser, String content) {
        Evaluation evaluation1 = new Evaluation();
        evaluation1.setEvaluatedUser(evaluatedUser);
        evaluation1.setEvaluatingUser(evaluatingUser);
        evaluation1.setContent(content);
        return evaluation1;
    }


    @Test
    @DisplayName("평가 저장")
    void saveEvaluationTest() {
        AddEvaluationRequestDTO addEvaluationRequestDTO = new AddEvaluationRequestDTO();
        addEvaluationRequestDTO.setEvaluatingUserId(user1.getId());
        addEvaluationRequestDTO.setContent("user1이 user2를 평가");

        EvaluationDTO evaluationDTO = evaluationService.saveEvaluation(user2.getId(), addEvaluationRequestDTO);

        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluationDTO.getEvaluationId());
        assertThat(optionalEvaluation).as("저장한 평가가 존재해야한다.")
                .isNotEmpty();
    }

    @Test
    @DisplayName("유저의 모든 평가 조회")
    void findEvaluationsOfUserTEst() {
        List<EvaluationDTO> evaluations = evaluationService.findEvaluationsOfUser(user1.getId());

        assertThat(evaluations).as("user1은 1개의 평가를 갖고 있어야 한다.")
                .hasSize(1);
    }

    @Test
    @DisplayName("평가 삭제")
    void deleteByIdTest() {
        evaluationService.deleteById(evaluation.getId());

        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluation.getId());

        assertThat(optionalEvaluation.isEmpty()).as("삭제한 평가를 조회하면 null이어야 한다.")
                .isTrue();
    }

    @Test
    @DisplayName("평가 수정")
    void updateEvaluationTest() {
        final String updateContent = "수정된 내용";
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(updateContent);
        evaluationService.updateEvaluation(evaluation.getId(), contentDTO);

        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(evaluation.getId());
        assertThat(optionalEvaluation).as("평가가 존재해야 한다.")
                .isNotEmpty();
        Evaluation updatedEvaluation = optionalEvaluation.get();

        assertThat(updatedEvaluation.getContent()).as("평가의 내용이 수정되어 있어야 한다.")
                .isEqualTo(updateContent);

    }
}