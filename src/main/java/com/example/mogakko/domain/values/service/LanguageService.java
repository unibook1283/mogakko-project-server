package com.example.mogakko.domain.values.service;

import com.example.mogakko.domain.values.domain.Language;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Transactional
    public Long saveLanguage(LanguageDTO languageDTO) {
        Language language = languageRepository.save(languageDTO.toEntity());
        return language.getId();
    }

    public LanguageDTO findOne(Long languageId) {
        Language language = languageRepository.findById(languageId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 languageId"));
        return new LanguageDTO(language);
    }

    public List<LanguageDTO> findAll() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream()
                .map(language -> new LanguageDTO(language))
                .collect(Collectors.toList());
    }
}
