package com.example.mogakko.domain.post.domain;

import com.example.mogakko.domain.post.domain.enums.Term;
import com.example.mogakko.domain.post.domain.enums.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("MOGAKKO")
@Getter
@Setter
public class Mogakko extends Post {

    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private Term term;
}
