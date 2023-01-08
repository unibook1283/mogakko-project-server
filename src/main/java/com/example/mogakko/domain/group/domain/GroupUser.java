package com.example.mogakko.domain.group.domain;

import com.example.mogakko.domain.group.enums.GroupStatus;
import com.example.mogakko.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GroupUser {

    @Id
    @GeneratedValue
    @Column(name = "group_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isMaster;

    //==생성 메서드==//
    public static GroupUser createGroupUser(Group group, User user) {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroup(group);
        groupUser.setUser(user);
        groupUser.setIsMaster(false);
        return groupUser;
    }

}
