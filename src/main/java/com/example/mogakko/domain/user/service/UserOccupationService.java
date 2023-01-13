package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserLanguage;
import com.example.mogakko.domain.user.domain.UserOccupation;
import com.example.mogakko.domain.user.repository.UserOccupationRepository;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.example.mogakko.domain.values.domain.Occupation;
import com.example.mogakko.domain.values.repository.OccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserOccupationService {

    private final UserRepository userRepository;
    private final OccupationRepository occupationRepository;
    private final UserOccupationRepository userOccupationRepository;

    @Transactional
    public Long prefer(Long userId, Long occupationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        // occupationId는 occupation name으로 찾을것이기 때문에 null이 아님이 보장됨.
        Occupation occupation = occupationRepository.findById(occupationId).get();

        UserOccupation userOccupation = new UserOccupation();
        userOccupation.setUser(user);
        userOccupation.setOccupation(occupation);

        UserOccupation saveUserOccupation = userOccupationRepository.save(userOccupation);

        return saveUserOccupation.getId();
    }

    public List<UserOccupation> findOccupationsOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        return userOccupationRepository.findByUser(user);
    }

}
