package com.example.mogakko.domain.report.repository;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.report.domain.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    List<PostReport> findByPost(Post post);
}
