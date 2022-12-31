package com.example.mogakko.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDTO {

    private Long userId;
    private String accessToken;
    private String refreshToken;

}
