package com.example.mogakko.domain.post.domain;

import com.example.mogakko.domain.group.domain.Group;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("STUDY")
@Getter
@Setter
public class Study extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_group_id")
    private Group s_group;
}
