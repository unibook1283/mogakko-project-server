package com.example.mogakko.domain.meeting.domain;

import com.example.mogakko.domain.group.domain.Group;
import com.example.mogakko.domain.group.domain.GroupUser;
import com.example.mogakko.domain.post.domain.values.PostOccupation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<MeetingUser> meetingUsers = new ArrayList<MeetingUser>();

    private String date;

    private String place;

    private String time;

    public void addMeetingUser(MeetingUser meetingUser) {
        meetingUsers.add(meetingUser);
        meetingUser.setMeeting(this);
    }

    public static Meeting createMeeting(Group group, MeetingUser meetingUser, String date, String place, String time) {
        Meeting meeting = new Meeting();
        meeting.setGroup(group);
        meeting.addMeetingUser(meetingUser);
        meeting.setDate(date);
        meeting.setPlace(place);
        meeting.setTime(time);
        return meeting;
    }

}
