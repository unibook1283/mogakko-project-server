package com.example.mogakko.domain.post.dto;

import com.example.mogakko.domain.post.domain.*;
import com.example.mogakko.domain.post.enums.Term;
import com.example.mogakko.domain.post.enums.Type;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDTO {

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

    private Long groupId;

    public PostRequestDTO(Post post) {
        this.userId = userId;
        this.title = title;
        this.type = type;
        this.content = content;
        this.deadline = deadline;
        this.term = term;
        this.languages = languages;
        this.locations = locations;
        this.occupations = occupations;
        this.groupId = groupId;
    }

    public Post toEntity(User user) {
        switch (type) {
            case PROJECT:
                Project project = new Project();
                setCommonInfo(user, project, type);
                project.setDeadline(deadline);
                return project;
            case MOGAKKO:
                Mogakko mogakko = new Mogakko();
                setCommonInfo(user, mogakko, type);
                mogakko.setDeadline(deadline);
                mogakko.setTerm(term);
                return mogakko;
            case QUESTION:
                Question question = new Question();
                setCommonInfo(user, question, type);
                return question;
            case STUDY:
                Study study = new Study();
                setCommonInfo(user, study, type);
                return study;
        }
        throw new IllegalArgumentException("잘못된 type");
    }

    private void setCommonInfo(User user, Post post, Type type) {
        post.setUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setDtype(type.name());
    }

}
