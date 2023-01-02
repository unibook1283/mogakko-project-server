package com.example.mogakko.domain.user.dto;

import com.example.mogakko.domain.values.dto.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProfileRequestDTO {


    private String nickname;

    private String oneLineIntroduction;

    private String phoneNumber;

    private String githubAddress;

    private String picture;

    private List<LanguageDTO> languages;

    private List<LocationDTO> locations;

    private List<OccupationDTO> occupations;

}
