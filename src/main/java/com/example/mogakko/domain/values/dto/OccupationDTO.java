package com.example.mogakko.domain.values.dto;

import com.example.mogakko.domain.values.domain.Occupation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OccupationDTO {

    private Long id;
    private String name;

    public OccupationDTO(Occupation occupation) {
        this.id = occupation.getId();
        this.name = occupation.getName();
    }

    public Occupation toEntity() {
        Occupation occupation = new Occupation();
        occupation.setId(id);
        occupation.setName(name);
        return occupation;
    }
}
