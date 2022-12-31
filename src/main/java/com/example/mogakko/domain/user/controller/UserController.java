package com.example.mogakko.domain.user.controller;

import com.example.mogakko.domain.user.dto.*;
import com.example.mogakko.domain.user.service.JwtService;
import com.example.mogakko.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public UserJoinResponseDTO create(@RequestBody @Valid UserJoinRequestDTO userAuthDTO) {
        return userService.join(userAuthDTO);
    }

    @PostMapping("/login") // 로그인, 토큰이 필요하지 않는 경로
    public JwtTokenDTO login(@RequestBody @Valid UserLoginRequestDTO userAuthDTO) {
        UserDTO dbUser = userService.findByUsername(userAuthDTO.getUsername());

        if(dbUser.getPassword().equals(userAuthDTO.getPassword())) { // 유효한 사용자일 경우
            String accessToken = jwtService.createToken(dbUser.getId());    // 사용자 정보로 accessToken 생성
            String refreshToken = jwtService.createRefreshToken();  // refreshToken 생성
            return new JwtTokenDTO(dbUser.getId(), accessToken, refreshToken);
        } else {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
    }

}
