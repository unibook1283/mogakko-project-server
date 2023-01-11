package com.example.mogakko.domain.report.domain;

import com.example.mogakko.domain.baseTime.BaseTimeEntity;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostReport extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_report_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

}
