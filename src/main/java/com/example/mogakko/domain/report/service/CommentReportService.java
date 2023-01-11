package com.example.mogakko.domain.report.service;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.comment.repository.CommentRepository;
import com.example.mogakko.domain.report.domain.CommentReport;
import com.example.mogakko.domain.report.dto.CommentReportDTO;
import com.example.mogakko.domain.report.dto.CommentReportRequestDTO;
import com.example.mogakko.domain.report.repository.CommentReportRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentReportDTO saveCommentReport(CommentReportRequestDTO commentReportRequestDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(commentReportRequestDTO.getCommentId());
        Comment comment = optionalComment.orElseThrow(() -> new IllegalArgumentException("잘못된 commentId"));

        Optional<User> optionalUser = userRepository.findById(commentReportRequestDTO.getUserId());
        User user = optionalUser.orElseThrow(() -> new IllegalArgumentException("잘못된 userId"));

        CommentReport commentReport = new CommentReport();
        commentReport.setComment(comment);
        commentReport.setUser(user);
        commentReport.setContent(commentReportRequestDTO.getContent());

        CommentReport saveCommentReport = commentReportRepository.save(commentReport);
        return new CommentReportDTO(saveCommentReport);
    }

    public List<CommentReportDTO> findCommentReports(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = optionalComment.orElseThrow(() -> new IllegalArgumentException("잘못된 commentId"));

        List<CommentReport> commentReports = commentReportRepository.findByComment(comment);

        return commentReports.stream()
                .map(commentReport -> new CommentReportDTO(commentReport))
                .collect(Collectors.toList());
    }
}
