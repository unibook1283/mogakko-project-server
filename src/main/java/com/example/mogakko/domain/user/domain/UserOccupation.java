package com.example.mogakko.domain.user.domain;

import com.example.mogakko.domain.values.domain.Occupation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserOccupation {

    @Id
    @GeneratedValue
    @Column(name = "user_occupation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getOccupations().add(this);
    }

}
