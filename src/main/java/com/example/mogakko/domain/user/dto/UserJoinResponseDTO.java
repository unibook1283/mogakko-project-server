package com.example.mogakko.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinResponseDTO {

    private Long userId;
    private String username;
    private String password;

}
