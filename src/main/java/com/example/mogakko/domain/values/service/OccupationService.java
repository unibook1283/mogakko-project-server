package com.example.mogakko.domain.values.service;

import com.example.mogakko.domain.values.domain.Occupation;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import com.example.mogakko.domain.values.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OccupationService {

    @Autowired
    OccupationRepository occupationRepository;

    public Long saveOccupation(OccupationDTO occupationDTO) {
        Occupation occupation = occupationRepository.save(occupationDTO.toEntity());
        return occupation.getId();
    }

    public OccupationDTO findOne(Long occupationId) {
        Occupation occupation = occupationRepository.findById(occupationId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 occupationId"));
        return new OccupationDTO(occupation);
    }

    public List<OccupationDTO> findAll() {
        List<Occupation> occupations = occupationRepository.findAll();
        return occupations.stream()
                .map(occupation -> new OccupationDTO(occupation))
                .collect(Collectors.toList());
    }

}
