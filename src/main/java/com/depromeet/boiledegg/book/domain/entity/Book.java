package com.depromeet.boiledegg.book.domain.entity;

import com.depromeet.boiledegg.book.domain.BookCategory;
import com.depromeet.boiledegg.book.domain.BookStatus;
import com.depromeet.boiledegg.common.domain.LikeCount;
import com.depromeet.boiledegg.common.domain.entity.BookCategoriesConverter;
import com.depromeet.boiledegg.common.domain.entity.base.CreateByAuditEntity;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table // TODO 책을 여러개 가지고 있을 수 있음 (uniqueConstraints = @UniqueConstraint(columnNames = {"owner", "isbn"}))
@Entity
public class Book extends CreateByAuditEntity {

    @Getter
    @Column(nullable = false)
    private String isbn;

    @Getter
    @Column(nullable = true, length = 4_000)
    @Convert(converter = BookCategoriesConverter.class)
    private Set<BookCategory> bookCategories = new HashSet<>(BookCategory.size());

    @Getter
    @Embedded
    private LikeCount likeCount;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status;

    @Builder
    public Book(
            final String isbn,
            final Set<BookCategory> bookCategories
    ) {
        this.isbn = isbn;
        this.bookCategories = bookCategories;
        likeCount = LikeCount.getDefault();
        status = BookStatus.WAIT;
    }

    public Book like(final SessionUser user) {
        // TODO 중복 추천 방지, 내 책 추천 방지

        likeCount = likeCount.increment();
        return this;
    }

    public int getLikeCountValue() {
        return likeCount.getLikeCount();
    }

    public void borrow() {
        status = status.borrow();
    }

    public void returns() {
        status = status.returns();
    }

    public void reject() {
        status = status.reject();
    }

    public void cancel() {
        status = status.cancel();
    }

    public void take() {
        status = status.take();
    }

    public void verifyBorrow() {
        status.verifyAlreadyBorrow();
    }
}
