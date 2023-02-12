package com.example.mogakko.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private Long userId;
    private Boolean isAuth;
    private Boolean isAdmin;
    private int role;
}
