package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserLanguage;
import com.example.mogakko.domain.user.repository.UserLanguageRepository;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.example.mogakko.domain.values.domain.Language;
import com.example.mogakko.domain.values.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLanguageService {

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final UserLanguageRepository userLanguageRepository;

    public Long prefer(Long userId, Long languageId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        // languageId는 language name으로 찾을것이기 때문에 null이 아님이 보장됨.
        Language language = languageRepository.findById(languageId).get();

        UserLanguage userLanguage = new UserLanguage();
        userLanguage.setUser(user);
        userLanguage.setLanguage(language);

        UserLanguage saveUserLanguage = userLanguageRepository.save(userLanguage);

        return saveUserLanguage.getId();
    }

    public List<UserLanguage> findLanguagesOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        return userLanguageRepository.findByUser(user);
    }

}
