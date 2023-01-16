package com.example.mogakko.domain.report.service;

import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.comment.exception.CommentNotFoundException;
import com.example.mogakko.domain.comment.repository.CommentRepository;
import com.example.mogakko.domain.report.domain.CommentReport;
import com.example.mogakko.domain.report.dto.CommentReportDTO;
import com.example.mogakko.domain.report.dto.CommentReportRequestDTO;
import com.example.mogakko.domain.report.repository.CommentReportRepository;
import com.example.mogakko.domain.user.domain.User;
import com.example.mogakko.domain.user.exception.UserNotFoundException;
import com.example.mogakko.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentReportService {

    private final CommentReportRepository commentReportRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentReportDTO saveCommentReport(CommentReportRequestDTO commentReportRequestDTO) {
        Comment comment = commentRepository.findById(commentReportRequestDTO.getCommentId())
                .orElseThrow(CommentNotFoundException::new);

        User user = userRepository.findById(commentReportRequestDTO.getUserId())
                .orElseThrow(UserNotFoundException::new);

        CommentReport commentReport = new CommentReport();
        commentReport.setComment(comment);
        commentReport.setUser(user);
        commentReport.setContent(commentReportRequestDTO.getContent());

        CommentReport saveCommentReport = commentReportRepository.save(commentReport);
        return new CommentReportDTO(saveCommentReport);
    }

    public List<CommentReportDTO> findCommentReports(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        List<CommentReport> commentReports = commentReportRepository.findByComment(comment);

        return commentReports.stream()
                .map(commentReport -> new CommentReportDTO(commentReport))
                .collect(Collectors.toList());
    }
}
