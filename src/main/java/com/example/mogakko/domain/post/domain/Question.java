package com.example.mogakko.domain.post.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("QUESTION")
@Getter
@Setter
public class Question extends Post {
}
