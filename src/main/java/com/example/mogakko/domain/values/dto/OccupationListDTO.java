package com.example.mogakko.domain.values.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccupationListDTO {

    private List<OccupationDTO> occupations;

}
