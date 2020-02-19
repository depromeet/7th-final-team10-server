package com.depromeet.boiledegg.transaction.domain.entity;

import com.depromeet.boiledegg.book.domain.entity.Book;
import com.depromeet.boiledegg.common.domain.entity.base.aggregate.CreateByAuditAggregateEntity;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.notification.domain.NotificationEvent;
import com.depromeet.boiledegg.notification.domain.entity.Notification;
import com.depromeet.boiledegg.transaction.domain.TransactionStatus;
import com.depromeet.boiledegg.transaction.domain.event.StatusChangeEvent;
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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Slf4j
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(columnList = "owner"),
                @Index(columnList = "bookId, owner")
        }
)
@Entity
public class Transaction extends CreateByAuditAggregateEntity<Transaction> {

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
            final Book book,
            final long userId
    ) {
        book.verifyBorrow();

        this.book = book;
        this.status = TransactionStatus.DEFAULT;

        registerEvent(Notification.builder()
                .fromUserId(userId)
                .toUserId(book.getOwner())
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .build());
    }

    public Transaction confirm(final SessionUser user) {
        verifyLander(user);
        book.borrow();

        return changeStatus(TransactionStatus.BORROWED);
    }

    public Transaction reject(final SessionUser user) {
        verifyLander(user);
        book.reject();

        return changeStatus(TransactionStatus.REJECT);
    }

    public Transaction cancel(final SessionUser user) {
        verifyMatchOwner(user);
        book.cancel();

        return changeStatus(TransactionStatus.CANCEL);
    }

    public Transaction returns(final SessionUser user) {
        verifyMatchOwner(user);
        book.returns();

        return changeStatus(TransactionStatus.RETURN);
    }

    public Transaction take(final SessionUser user) {
        verifyLander(user);
        book.take();

        return changeStatus(TransactionStatus.COMPLETED);
    }

    private void verifyLander(final SessionUser user) {
        book.verifyMatchOwner(user);
    }

    private Transaction changeStatus(final TransactionStatus status) {
        registerEvent(StatusChangeEvent.builder()
                .borrower(getOwner())
                .bookOwner(book.getOwner())
                .before(this.status)
                .after(status)
                .build());

        this.status = this.status.change(status);
        return this;
    }
}
