package com.example.mogakko.domain.group.domain;

import com.example.mogakko.domain.group.enums.GroupStatus;
import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.post.domain.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"group\"")
@Getter
@Setter
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus;

    @OneToMany(mappedBy = "group")
    private List<GroupUser> groupUsers = new ArrayList<GroupUser>();

    @OneToMany(mappedBy = "group")
    private List<GroupAdmission> groupAdmissions = new ArrayList<GroupAdmission>();

    @OneToOne(mappedBy = "group")
    private Post post;

    @OneToMany(mappedBy = "s_group")
    private List<Study> studyPosts = new ArrayList<Study>();

    public void addGroupUser(GroupUser groupUser) {
        groupUsers.add(groupUser);
        groupUser.setGroup(this);
    }

    public void addStudyPost(Study study) {
        studyPosts.add(study);
        study.setS_group(this);
    }

    //==생성 메서드==//
    public static Group createGroup(GroupUser groupUser) {
        Group group = new Group();
        group.setGroupStatus(GroupStatus.RECRUIT);
        group.addGroupUser(groupUser);
        return group;
    }

}
