package com.depromeet.boiledegg.transaction.domain.entity;

import com.depromeet.boiledegg.book.domain.entity.Book;
import com.depromeet.boiledegg.common.domain.entity.AuditEntity;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.transaction.domain.TransactionStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Slf4j
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                // TODO index
        }
)
@Entity
public class Transaction extends AuditEntity {

    // TODO change event

    @Getter
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Builder
    public Transaction(
            final Book book
    ) {
        this.book = book;
        this.status = TransactionStatus.DEFAULT;
    }

    public Transaction confirm(final SessionUser user) {
        verifyLander(user);

        changeStatus(TransactionStatus.BORROWED);
        book.borrow();

        return this;
    }

    public Transaction reject(final SessionUser user) {
        verifyLander(user);

        changeStatus(TransactionStatus.REJECT);
        return this;
    }

    public Transaction cancel(final SessionUser user) {
        verifyMatchOwner(user);

        changeStatus(TransactionStatus.CANCEL);
        return this;
    }

    public Transaction returns(final SessionUser user) {
        verifyMatchOwner(user);

        changeStatus(TransactionStatus.RETURN);
        return this;
    }

    public Transaction take(final SessionUser user) {
        verifyLander(user);

        changeStatus(TransactionStatus.COMPLETED);
        return this;
    }

    private void verifyLander(final SessionUser user) {
        book.verifyMatchOwner(user);
    }

    private void changeStatus(final TransactionStatus status) {
        this.status = this.status.change(status);
    }
}
