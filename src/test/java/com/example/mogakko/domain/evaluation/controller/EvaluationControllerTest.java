package com.example.mogakko.domain.evaluation.controller;

import com.example.mogakko.domain.evaluation.domain.Evaluation;
import com.example.mogakko.domain.evaluation.dto.AddEvaluationRequestDTO;
import com.example.mogakko.domain.evaluation.dto.ContentDTO;
import com.example.mogakko.domain.evaluation.repository.EvaluationRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class EvaluationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UserRepository userRepository;

    private String token;
    private User user1, user2;
    private Evaluation evaluation;

    @BeforeAll
    void getToken() {
        token = jwtService.createToken(1L);
    }

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
    void addEvaluationHttpRequest() throws Exception {
        final String content = "user1이 user2를 평가";
        AddEvaluationRequestDTO addEvaluationRequestDTO = new AddEvaluationRequestDTO();
        addEvaluationRequestDTO.setEvaluatingUserId(user1.getId());
        addEvaluationRequestDTO.setContent(content);

        mockMvc.perform(post("/users/{userId}/evaluations", user2.getId())
                        .header("accessToken", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addEvaluationRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.evaluatedUserId").value(user2.getId()))
                .andExpect(jsonPath("$.evaluatingUserId").value(user1.getId()))
                .andExpect(jsonPath("$.evaluatingUserNickname").value(user1.getNickname()))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @DisplayName("유저의 모든 평가 조회")
    void getEvaluationHttpRequest() throws Exception {
        mockMvc.perform(get("/users/{userId}/evaluations", user1.getId())
                        .header("accessToken", token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].evaluationId").value(evaluation.getId()))
                .andExpect(jsonPath("$[0].evaluatedUserId").value(user1.getId()))
                .andExpect(jsonPath("$[0].evaluatingUserId").value(user2.getId()))
                .andExpect(jsonPath("$[0].evaluatingUserNickname").value(user2.getNickname()))
                .andExpect(jsonPath("$[0].content").value("user2가 user1을 평가"));
    }

    @Test
    @DisplayName("평가 삭제")
    void deleteEvaluationHttpRequest() throws Exception {
        mockMvc.perform(delete("/evaluations/{evaluationId}", evaluation.getId())
                        .header("accessToken", token))
                .andExpect(status().isOk());

        assertThat(evaluationRepository.findById(evaluation.getId()))
                .isEmpty();
    }

    @Test
    @DisplayName("평가 수정")
    void updateEvaluationHttpRequest() throws Exception {
        final String content = "수정된 내용";

        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(content);

        mockMvc.perform(patch("/evaluations/{evaluationId}", evaluation.getId())
                        .header("accessToken", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contentDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.evaluationId").value(evaluation.getId()))
                .andExpect(jsonPath("$.evaluatedUserId").value(user1.getId()))
                .andExpect(jsonPath("$.evaluatingUserId").value(user2.getId()))
                .andExpect(jsonPath("$.evaluatingUserNickname").value(user2.getNickname()))
                .andExpect(jsonPath("$.content").value(content));
    }
}