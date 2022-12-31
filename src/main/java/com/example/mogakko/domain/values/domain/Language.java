package com.example.mogakko.domain.values.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue
    @Column(name = "language_id")
    private Long id;

    private String name;

}
