package com.example.mogakko.domain.values.service;

import com.example.mogakko.domain.values.domain.Occupation;
import com.example.mogakko.domain.values.dto.OccupationDTO;
import com.example.mogakko.domain.values.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<Occupation> optionalOccupation = occupationRepository.findById(occupationId);
        if (optionalOccupation.isPresent()) {
            return new OccupationDTO(optionalOccupation.get());
        } else {
            return null;
        }
    }

}
