package com.example.mogakko.domain.group.domain;

import com.example.mogakko.domain.group.enums.Status;
import com.example.mogakko.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "group", fetch = FetchType.LAZY)
    private Post post;

    @OneToMany(mappedBy = "group")
    private List<GroupUser> groupUsers;

}
