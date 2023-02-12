package com.example.mogakko.domain.post.domain;

import com.example.mogakko.domain.baseTime.BaseTimeEntity;
import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.post.domain.values.PostLanguage;
import com.example.mogakko.domain.post.domain.values.PostLocation;
import com.example.mogakko.domain.post.domain.values.PostOccupation;
import com.example.mogakko.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(insertable = false, updatable = false)
    private String dtype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    private String title;

    @Column(length = 1000)
    private String content;

    @OneToMany(mappedBy = "post")
    private List<PostLanguage> languages = new ArrayList<PostLanguage>();

    @OneToMany(mappedBy = "post")
    private List<PostLocation> locations = new ArrayList<PostLocation>();

    @OneToMany(mappedBy = "post")
    private List<PostOccupation> occupations = new ArrayList<PostOccupation>();

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    public void setGroup(Group group) {
        this.group = group;
        group.setPost(this);
    }

}
