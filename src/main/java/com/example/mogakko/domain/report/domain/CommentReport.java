package com.example.mogakko.domain.report.domain;

import com.example.mogakko.domain.baseTime.BaseTimeEntity;
import com.example.mogakko.domain.comment.domain.Comment;
import com.example.mogakko.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommentReport extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

}
