package com.example.mogakko.domain.post.domain.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.values.domain.Occupation;
import com.example.mogakko.domain.values.domain.Occupation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostOccupation {

    @Id
    @GeneratedValue
    @Column(name = "post_occupation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;

    //==연관관계 메서드==//
    public void setPost(Post post) {
        this.post = post;
        post.getOccupations().add(this);
    }

    //==생성 메서드==//
    public static PostOccupation createPostOccupation(Post post, Occupation occupation) {
        PostOccupation postOccupation = new PostOccupation();
        postOccupation.setPost(post);
        postOccupation.setOccupation(occupation);

        return postOccupation;
    }
    
}
