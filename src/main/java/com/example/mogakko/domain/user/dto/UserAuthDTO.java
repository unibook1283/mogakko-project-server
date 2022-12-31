package com.example.mogakko.domain.user.dto;

import com.example.mogakko.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UserAuthDTO {

    private String username;
    private String password;

    public User toEntity() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
