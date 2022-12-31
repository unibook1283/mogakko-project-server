package com.example.mogakko.domain.values.repository;

import com.example.mogakko.domain.values.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
