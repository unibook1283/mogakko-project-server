package com.example.mogakko.domain.user.domain;

import com.example.mogakko.domain.values.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserLocation {

    @Id
    @GeneratedValue
    @Column(name = "user_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getLocations().add(this);
    }

}
