package com.depromeet.boiledegg.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class PostBody {

    @Getter
    @Column(length = 500, nullable = false)
    private String title;

    @Getter
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    private PostBody(
            final String title,
            final String content
    ) {
        this.title = title;
        this.content = content;
    }
}
