package com.example.mogakko.domain.meeting.domain;

import com.example.mogakko.domain.group.domain.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Meeting {

    @Id
    @GeneratedValue
    @Column(name = "meeting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "meeting")
    private List<MeetingUser> meetingUsers;

    private String date;

    private String place;

    private String time;

}
