package com.example.mogakko.domain.report.repository;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.report.domain.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    List<CommentReport> findByComment(Comment comment);
}
