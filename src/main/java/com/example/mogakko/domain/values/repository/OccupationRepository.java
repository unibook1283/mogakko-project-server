package com.example.mogakko.domain.values.repository;

import com.example.mogakko.domain.values.domain.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationRepository extends JpaRepository<Occupation, Long> {
}
