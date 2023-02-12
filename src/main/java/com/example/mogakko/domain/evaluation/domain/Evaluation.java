package com.example.mogakko.domain.evaluation.domain;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue
    @Column(name = "evaluation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluated_user_id")
    private User evaluatedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluating_user_id")
    private User evaluatingUser;

    private String content;

}
