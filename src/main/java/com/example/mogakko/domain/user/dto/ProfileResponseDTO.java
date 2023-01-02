package com.example.mogakko.domain.user.dto;

import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProfileResponseDTO {

    private Long userId;

    private String username;

    private String nickname;

    private String oneLineIntroduction;

    private String phoneNumber;

    private String githubAddress;

    private String picture;

    private List<LanguageDTO> languages;

    private List<LocationDTO> locations;

    private List<OccupationDTO> occupations;

    public ProfileResponseDTO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.oneLineIntroduction = user.getOneLineIntroduction();
        this.phoneNumber = user.getPhoneNumber();
        this.githubAddress = user.getGithubAddress();
        this.picture = user.getPicture();
        this.languages = user.getLanguages()
                .stream().map(l -> new LanguageDTO(l.getLanguage()))
                .collect(Collectors.toList());
        this.locations = user.getLocations()
                .stream().map(l -> new LocationDTO(l.getLocation()))
                .collect(Collectors.toList());
        this.occupations = user.getOccupations()
                .stream().map(o -> new OccupationDTO(o.getOccupation()))
                .collect(Collectors.toList());
    }
}
