package com.example.mogakko.domain.evaluation.repository;

import com.example.mogakko.domain.evaluation.domain.Evaluation;
import com.example.mogakko.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByEvaluatedUser(User user);

}
