package com.example.mogakko.domain.values.dto;

import com.example.mogakko.domain.values.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LanguageDTO {

    private Long languageId;
    private String languageName;

    public LanguageDTO(Language language) {
        this.languageId = language.getId();
        this.languageName = language.getName();
    }

    public Language toEntity() {
        Language language = new Language();
        language.setId(languageId);
        language.setName(languageName);
        return language;
    }

}
