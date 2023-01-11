package com.example.mogakko.domain.report.repository;

import com.example.mogakko.domain.report.domain.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
}
