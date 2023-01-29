package com.example.mogakko.domain.values.controller;

import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LanguageListDTO;
import com.example.mogakko.domain.values.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("/languages")
    public LanguageListDTO getLanguages() {
        List<LanguageDTO> languages = languageService.findAll();
        return new LanguageListDTO(languages);
    }

}
