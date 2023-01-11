package com.example.mogakko.domain.report.repository;

import com.example.mogakko.domain.report.domain.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
}
