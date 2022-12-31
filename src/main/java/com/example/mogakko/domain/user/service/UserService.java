package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.dto.UserAuthDTO;
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

    public UserJoinResponseDTO join(UserAuthDTO userAuthDTO) {
        User user = userRepository.save(userAuthDTO.toEntity());
        return new UserJoinResponseDTO(user.getId(), user.getUsername(), user.getPassword());
    }

    public UserDTO findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }
        return new UserDTO(userOptional.get());
    }

}
