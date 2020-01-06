package com.depromeet.boiledegg.book.domain.entity;

import com.depromeet.boiledegg.common.domain.LikeCount;
import com.depromeet.boiledegg.common.domain.entity.AuditEntity;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table // TODO (uniqueConstraints = @UniqueConstraint(columnNames = {"owner", "isbn"}))
@Entity
public class Book extends AuditEntity {

    @Getter
    @Column(nullable = false)
    private String isbn;

    @Getter
    @Embedded
    private LikeCount likeCount;

    @Builder
    public Book(
            final String isbn
    ) {
        this.isbn = isbn;
        likeCount = LikeCount.getDefault();
    }

    public Book like(final SessionUser user) {
        verifyMatchOwner(user);

        likeCount = likeCount.increment();
        return this;
    }

    public int getLikeCountValue() {
        return likeCount.getLikeCount();
    }

    public void borrow() {

    }
}
