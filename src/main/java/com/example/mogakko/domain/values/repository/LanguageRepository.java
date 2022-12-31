package com.example.mogakko.domain.values.repository;

import com.example.mogakko.domain.values.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
