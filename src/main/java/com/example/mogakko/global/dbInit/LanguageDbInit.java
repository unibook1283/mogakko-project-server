package com.example.mogakko.global.dbInit;

import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LanguageDbInit {

    private final LanguageService languageService;

//    @PostConstruct
    private void insertLanguagesIntoDb() {
        List<String> languages = List.of(
                // 프론트엔드
                "JavaScript",
                "TypeScript",
                "React",
                "Vue",
                "Svelte",
                "Angular",
                // 백엔드
                "Spring",
                "Laravel",
                "NodeJS",
                "NestJS",
                "Go",
                "Kotlin",
                "Express",
                "Django",
                "php",
                "Flask",
                "Ruby on Rails",
                // db
                "GraphQL",
                "MySQL",
                "Oracle",
                "MongoDB",
                "PostgreSQL",
                "MariaDB",
                // 모바일
                "Flutter",
                "Swift",
                "ReactNative",
                "Unity",
                // 기타
                "aws",
                "Kubernetes",
                "Docker",
                "Git",
                "Figma",
                "Zeplin",
                "Jest",
                "Firebase"
        );

        languages.stream()
                .forEach(language -> {
                    LanguageDTO languageDTO = new LanguageDTO();
                    languageDTO.setLanguageName(language);
                    languageService.saveLanguage(languageDTO);
                });
    }
}
