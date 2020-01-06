package com.depromeet.boiledegg.post.domain.entity;

import com.depromeet.boiledegg.post.domain.PostBody;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
public class Post {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Embedded
    private PostBody postBody;

    @Builder
    private Post(final PostBody postBody) {
        this.postBody = postBody;
    }
}
