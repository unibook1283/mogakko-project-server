package com.example.mogakko.domain.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String oneLineIntroduction;

    private String phoneNumber;

    private String githubAddress;

    private String picture;

    private boolean admin;


}
