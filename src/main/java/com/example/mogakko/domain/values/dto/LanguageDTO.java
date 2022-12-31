package com.example.mogakko.domain.values.dto;

import com.example.mogakko.domain.values.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LanguageDTO {

    private Long id;
    private String name;

    public LanguageDTO(Language language) {
        this.id = language.getId();
        this.name = language.getName();
    }

    public Language toEntity() {
        Language language = new Language();
        language.setId(id);
        language.setName(name);
        return language;
    }

}
