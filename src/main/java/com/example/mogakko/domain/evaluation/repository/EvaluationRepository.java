package com.example.mogakko.domain.evaluation.repository;

import com.example.mogakko.domain.evaluation.domain.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
