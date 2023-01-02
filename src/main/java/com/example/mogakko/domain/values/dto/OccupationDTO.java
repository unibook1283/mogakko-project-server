package com.example.mogakko.domain.values.dto;

import com.example.mogakko.domain.values.domain.Occupation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OccupationDTO {

    private Long occupationId;
    private String occupationName;

    public OccupationDTO(Occupation occupation) {
        this.occupationId = occupation.getId();
        this.occupationName = occupation.getName();
    }

    public Occupation toEntity() {
        Occupation occupation = new Occupation();
        occupation.setId(occupationId);
        occupation.setName(occupationName);
        return occupation;
    }
}
