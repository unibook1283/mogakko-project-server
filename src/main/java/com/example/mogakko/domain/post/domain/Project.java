package com.example.mogakko.domain.post.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PROJECT")
@Getter
@Setter
public class Project extends Post {

    private LocalDateTime deadline;

}
