package com.example.mogakko.domain.user.controller;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.dto.*;
import com.example.mogakko.domain.user.exception.LoginFailedException;
import com.example.mogakko.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/register")
    public UserJoinResponseDTO create(@RequestBody @Valid UserJoinRequestDTO userAuthDTO) {
        return userService.join(userAuthDTO);
    }

    @PostMapping("/users/login")
    public void login(@RequestBody @Valid UserLoginRequestDTO userAuthDTO, HttpServletRequest request) {
        User loginMember = userService.login(userAuthDTO.getUsername(), userAuthDTO.getPassword());

        if (loginMember == null) {
            throw new LoginFailedException();
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginMember.getId());
    }

    @PostMapping("/users/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @GetMapping("/users/auth")
    public AuthDto auth(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();

        Boolean isAuth = true;
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            isAuth = false;
        }
        return new AuthDto(isAuth, false, 0);
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
