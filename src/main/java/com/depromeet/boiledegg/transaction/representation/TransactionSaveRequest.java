package com.depromeet.boiledegg.transaction.representation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class TransactionSaveRequest {

    private Long bookId;

    @Builder
    public TransactionSaveRequest(
            final Long bookId
    ) {
        this.bookId = bookId;
    }
}
