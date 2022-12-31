package com.example.mogakko.domain.user.dto;

import com.example.mogakko.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String oneLineIntroduction;

    private String phoneNumber;

    private String githubAddress;

    private String picture;

    private Boolean admin;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.oneLineIntroduction = user.getOneLineIntroduction();
        this.phoneNumber = user.getPhoneNumber();
        this.githubAddress = user.getGithubAddress();
        this.picture = user.getPicture();
        this.admin = user.getAdmin();
    }
}
