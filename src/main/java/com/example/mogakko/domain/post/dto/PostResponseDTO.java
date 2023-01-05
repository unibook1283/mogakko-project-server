package com.example.mogakko.domain.post.dto;

import com.example.mogakko.domain.post.domain.*;
import com.example.mogakko.domain.post.enums.Term;
import com.example.mogakko.domain.post.enums.Type;
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
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDTO {

    private Long postId;

    private Long userId;

    private String nickname;

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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostResponseDTO(Post post) {
        this.setPostId(post.getId());
        this.setUserId(post.getUser().getId());
        this.setNickname(post.getUser().getNickname());
        this.setType(Type.valueOf(post.getDtype()));
        this.setTitle(post.getTitle());
        this.setContent(post.getContent());
        switch (type) {
            case PROJECT:
                Project project = (Project) post;
                this.setDeadline(project.getDeadline());
                break;
            case MOGAKKO:
                Mogakko mogakko = (Mogakko) post;
                this.setDeadline(mogakko.getDeadline());
                this.setTerm(mogakko.getTerm());
                break;
            case QUESTION:
                Question question = (Question) post;
                break;
            case STUDY:
                Study study = (Study) post;
                break;
        }
        this.languages = post.getLanguages()
                .stream().map(l -> new LanguageDTO(l.getLanguage()))
                .collect(Collectors.toList());
        this.locations = post.getLocations()
                .stream().map(l -> new LocationDTO(l.getLocation()))
                .collect(Collectors.toList());
        this.occupations = post.getOccupations()
                .stream().map(o -> new OccupationDTO(o.getOccupation()))
                .collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
