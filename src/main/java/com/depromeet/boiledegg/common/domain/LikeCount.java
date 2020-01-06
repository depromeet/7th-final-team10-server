package com.depromeet.boiledegg.common.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LikeCount {

    private static final int INCREMENT_VALUE = 1;

    @Getter
    @Column(nullable = false)
    private int likeCount;

    private LikeCount(final int likeCount) {
        this.likeCount = likeCount;
    }

    public static LikeCount getDefault() {
        return new LikeCount();
    }

    public LikeCount increment() {
        return new LikeCount(likeCount + INCREMENT_VALUE);
    }
}
