package com.example.mogakko.domain.user.service;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.domain.UserLanguage;
import com.example.mogakko.domain.user.domain.UserLocation;
import com.example.mogakko.domain.user.domain.UserOccupation;
import com.example.mogakko.domain.user.dto.*;
import com.example.mogakko.domain.user.exception.LoginFailedException;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserLanguageRepository;
import com.example.mogakko.domain.user.repository.UserLocationRepository;
import com.example.mogakko.domain.user.repository.UserOccupationRepository;
import com.example.mogakko.domain.user.repository.UserRepository;
import com.example.mogakko.domain.values.domain.Language;
import com.example.mogakko.domain.values.domain.Location;
import com.example.mogakko.domain.values.domain.Occupation;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import com.example.mogakko.domain.values.exception.LanguageNotFoundException;
import com.example.mogakko.domain.values.exception.LocationNotFoundException;
import com.example.mogakko.domain.values.exception.OccupationNotFoundException;
import com.example.mogakko.domain.values.repository.LanguageRepository;
import com.example.mogakko.domain.values.repository.LocationRepository;
import com.example.mogakko.domain.values.repository.OccupationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserLanguageRepository userLanguageRepository;
    private final UserLocationRepository userLocationRepository;
    private final UserOccupationRepository userOccupationRepository;
    private final LanguageRepository languageRepository;
    private final LocationRepository locationRepository;
    private final OccupationRepository occupationRepository;

    @Transactional
    public UserJoinResponseDTO join(UserJoinRequestDTO userAuthDTO) {
        User user = userRepository.save(userAuthDTO.toEntity());
        return new UserJoinResponseDTO(user.getId(), user.getUsername());
    }

    @Transactional
    public User login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    public ProfileResponseDTO getProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return new ProfileResponseDTO(user);
    }

    public UserDTO findOne(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return new UserDTO(user);
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

    @Transactional
    public ProfileResponseDTO saveUserProfile(Long userId, ProfileRequestDTO profileRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.setNickname(profileRequestDTO.getNickname());
        user.setOneLineIntroduction(profileRequestDTO.getOneLineIntroduction());
        user.setPhoneNumber(profileRequestDTO.getPhoneNumber());
        user.setGithubAddress(profileRequestDTO.getGithubAddress());
        user.setPicture(profileRequestDTO.getPicture());

        // 지금은 user의 userLanguage, userLocation, userOccupation을 모두 지웠다가 요청받은 profileDTO에 있는걸로 다시 저장하는 방식.
        // 성능이 몹시 별로임. 코드도 지저분.
        // 그렇다고 controller에서 여러 service를 호출하면, transaction이 제각각 걸린다는 문제.
        // 더 좋은 방식을 고민해보자. 변경된 것만 update하는 dynamicUpdate?
        // userLanguageService의 prefer을 사용하지 않은건 service간의 의존을 없애기 위함.

        // userLanguage
        userLanguageRepository.deleteAllByUser(user);
        user.getLanguages().clear();    // 별로임.

        List<LanguageDTO> languages = profileRequestDTO.getLanguages();
        languages.stream()
                .forEach(languageDTO -> {
                    Language language = languageRepository.findById(languageDTO.getLanguageId())
                            .orElseThrow(LanguageNotFoundException::new);

                    UserLanguage userLanguage = new UserLanguage();
                    userLanguage.setUser(user);
                    userLanguage.setLanguage(language);

                    userLanguageRepository.save(userLanguage);
                });


        // userLocation
        userLocationRepository.deleteAllByUser(user);
        user.getLocations().clear();

        List<LocationDTO> locations = profileRequestDTO.getLocations();
        locations.stream()
                .forEach(locationDTO -> {
                    Location location = locationRepository.findById(locationDTO.getLocationId())
                            .orElseThrow(LocationNotFoundException::new);

                    UserLocation userLocation = new UserLocation();
                    userLocation.setUser(user);
                    userLocation.setLocation(location);

                    userLocationRepository.save(userLocation);
                });


        // userOccupation
        userOccupationRepository.deleteAllByUser(user);
        user.getOccupations().clear();

        List<OccupationDTO> occupations = profileRequestDTO.getOccupations();
        occupations.stream()
                .forEach(occupationDTO -> {
                    Occupation occupation = occupationRepository.findById(occupationDTO.getOccupationId())
                            .orElseThrow(OccupationNotFoundException::new);

                    UserOccupation userOccupation = new UserOccupation();
                    userOccupation.setUser(user);
                    userOccupation.setOccupation(occupation);

                    userOccupationRepository.save(userOccupation);
                });

        return new ProfileResponseDTO(user);
    }


}
