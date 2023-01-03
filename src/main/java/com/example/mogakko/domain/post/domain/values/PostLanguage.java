package com.example.mogakko.domain.post.domain.values;

import com.example.mogakko.domain.post.domain.Post;
import com.example.mogakko.domain.values.domain.Language;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostLanguage {

    @Id
    @GeneratedValue
    @Column(name = "post_language_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    //==연관관계 메서드==//
    public void setPost(Post post) {
        this.post = post;
        post.getLanguages().add(this);
    }

    //==생성 메서드==//
    public static PostLanguage createPostLanguage(Post post, Language language) {
        PostLanguage postLanguage = new PostLanguage();
        postLanguage.setPost(post);
        postLanguage.setLanguage(language);

        return postLanguage;
    }

}
