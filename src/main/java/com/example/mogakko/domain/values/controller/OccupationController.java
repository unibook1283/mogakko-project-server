package com.example.mogakko.domain.values.controller;

import com.example.mogakko.domain.values.dto.OccupationDTO;
import com.example.mogakko.domain.values.dto.OccupationListDTO;
import com.example.mogakko.domain.values.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OccupationController {

    private final OccupationService occupationService;

    @GetMapping("/occupations")
    public OccupationListDTO getOccupations() {
        List<OccupationDTO> occupations = occupationService.findAll();
        return new OccupationListDTO(occupations);
    }

}
