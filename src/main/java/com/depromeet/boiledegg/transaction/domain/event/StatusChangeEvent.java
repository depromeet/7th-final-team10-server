package com.depromeet.boiledegg.transaction.domain.event;

import com.depromeet.boiledegg.transaction.domain.TransactionStatus;
import lombok.Builder;
import lombok.Data;

@Data
public final class StatusChangeEvent {

    private final long borrower;

    private final long bookOwner;

    private final TransactionStatus before;

    private final TransactionStatus after;

    private final String isbn;

    @Builder
    public StatusChangeEvent(
            final long borrower,
            final long bookOwner,
            final TransactionStatus before,
            final TransactionStatus after,
            final String isbn
    ) {
        this.borrower = borrower;
        this.bookOwner = bookOwner;
        this.before = before;
        this.after = after;
        this.isbn = isbn;
    }
}
