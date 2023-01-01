package com.example.mogakko.global.dbInit;

import com.example.mogakko.domain.values.dto.OccupationDTO;
import com.example.mogakko.domain.values.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OccupationDbInit {

    private final OccupationService occupationService;

    @PostConstruct
    private void insertOccupationsIntoDb() {
        List<String> occupations = List.of(
                "프론트엔드",
                "백엔드",
                "데이터",
                "모바일",
                "데브옵스",
                "보안",
                "시스템",
                "게임",
                "인공지능"
        );

        occupations.stream()
                .forEach(occupation -> {
                    OccupationDTO occupationDTO = new OccupationDTO();
                    occupationDTO.setName(occupation);
                    occupationService.saveOccupation(occupationDTO);
                });
    }
}
