package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.dto.UserJoinRequestDTO;
import com.example.mogakko.domain.user.dto.UserDTO;
import com.example.mogakko.domain.user.dto.UserJoinResponseDTO;
import com.example.mogakko.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserJoinResponseDTO join(UserJoinRequestDTO userAuthDTO) {
        User user = userRepository.save(userAuthDTO.toEntity());
        return new UserJoinResponseDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    public UserDTO findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return new UserDTO(userOptional.get());
        } else {
            return null;
        }
    }

    public UserDTO findByNickname(String nickname) {
        Optional<User> userOptional = userRepository.findByNickname(nickname);
        if (userOptional.isPresent()) {
            return new UserDTO(userOptional.get());
        } else {
            return null;
        }
    }

}
