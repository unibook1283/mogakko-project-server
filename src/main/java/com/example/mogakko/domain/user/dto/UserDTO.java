package com.example.mogakko.domain.user.dto;

import com.example.mogakko.domain.post.dto.PostResponseDTO;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.values.dto.LanguageDTO;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String oneLineIntroduction;

    private String phoneNumber;

    private String githubAddress;

    private String picture;

    private Boolean admin;

    private List<LanguageDTO> languages;

    private List<LocationDTO> locations;

    private List<OccupationDTO> occupations;

    private List<PostResponseDTO> posts;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.oneLineIntroduction = user.getOneLineIntroduction();
        this.phoneNumber = user.getPhoneNumber();
        this.githubAddress = user.getGithubAddress();
        this.picture = user.getPicture();
        this.admin = user.getAdmin();
        this.languages = user.getLanguages()
                .stream().map(l -> new LanguageDTO(l.getLanguage()))
                .collect(Collectors.toList());
        this.locations = user.getLocations()
                .stream().map(l -> new LocationDTO(l.getLocation()))
                .collect(Collectors.toList());
        this.occupations = user.getOccupations()
                .stream().map(o -> new OccupationDTO(o.getOccupation()))
                .collect(Collectors.toList());
        this.posts = user.getPosts()
                .stream().map(p -> new PostResponseDTO(p))
                .collect(Collectors.toList());
    }
}
