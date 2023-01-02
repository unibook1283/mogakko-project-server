package com.example.mogakko.domain.user.controller;

import com.example.mogakko.domain.user.dto.*;
import com.example.mogakko.domain.user.service.JwtService;
import com.example.mogakko.domain.user.service.UserService;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/users/register")
    public JwtTokenDTO create(@RequestBody @Valid UserJoinRequestDTO userAuthDTO) {
        UserJoinResponseDTO userJoinResponseDTO = userService.join(userAuthDTO);

        String accessToken = jwtService.createToken(userJoinResponseDTO.getUserId());
        String refreshToken = jwtService.createRefreshToken();

        return new JwtTokenDTO(userJoinResponseDTO.getUserId(), accessToken, refreshToken);
    }

    @PostMapping("/users/login") // 로그인, 토큰이 필요하지 않는 경로
    public JwtTokenDTO login(@RequestBody @Valid UserLoginRequestDTO userAuthDTO) {
        UserDTO dbUser = userService.findByUsername(userAuthDTO.getUsername());
        if (dbUser == null) throw new IllegalArgumentException("존재하지 않는 아이디입니다.");

        /**
         * 리팩토링. service로 빼야할 듯.
         */
        if (dbUser.getPassword().equals(userAuthDTO.getPassword())) { // 유효한 사용자일 경우
            String accessToken = jwtService.createToken(dbUser.getId());    // 사용자 정보로 accessToken 생성
            String refreshToken = jwtService.createRefreshToken();  // refreshToken 생성
            return new JwtTokenDTO(dbUser.getId(), accessToken, refreshToken);
        } else {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
    }

    @PostMapping("/users/username-redundancy")
    public RedundancyDTO checkUsernameRedundancy(@RequestBody UsernameDTO usernameDTO) {
        String username = usernameDTO.getUsername();
        UserDTO userDTO = userService.findByUsername(username);

        RedundancyDTO redundancyDTO = new RedundancyDTO();
        if (userDTO == null) redundancyDTO.setIsExist(false);
        else redundancyDTO.setIsExist(true);

        return redundancyDTO;
    }

    @PostMapping("/users/nickname-redundancy")
    public RedundancyDTO checkNicknameRedundancy(@RequestBody NicknameDTO nicknameDTO) {
        String nickname = nicknameDTO.getNickname();
        UserDTO userDTO = userService.findByNickname(nickname);

        RedundancyDTO redundancyDTO = new RedundancyDTO();
        if (userDTO == null) redundancyDTO.setIsExist(false);
        else redundancyDTO.setIsExist(true);

        return redundancyDTO;
    }

    @PostMapping("/users/{userId}")
    public ProfileResponseDTO saveUserProfile(@PathVariable Long userId, @RequestBody ProfileRequestDTO profileRequestDTO) {

        return userService.saveUserProfile(userId, profileRequestDTO);
    }

    @GetMapping("/users/{userId}")
    public ProfileResponseDTO getUserProfile(@PathVariable Long userId) {
        return userService.getProfileByUserId(userId);
    }

}
