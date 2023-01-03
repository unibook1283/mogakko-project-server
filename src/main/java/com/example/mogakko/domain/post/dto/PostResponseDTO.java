package com.example.mogakko.domain.post.dto;

import com.example.mogakko.domain.post.domain.enums.Term;
import com.example.mogakko.domain.post.domain.enums.Type;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDTO {

    private Long postId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String title;

    private String content;

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Term term;

    private List<LanguageDTO> languages;

    private List<LocationDTO> locations;

    private List<OccupationDTO> occupations;

}
