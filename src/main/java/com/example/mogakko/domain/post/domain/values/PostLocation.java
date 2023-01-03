package com.example.mogakko.domain.post.domain.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.values.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostLocation {

    @Id
    @GeneratedValue
    @Column(name = "post_location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    //==연관관계 메서드==//
    public void setPost(Post post) {
        this.post = post;
        post.getLocations().add(this);
    }

}
