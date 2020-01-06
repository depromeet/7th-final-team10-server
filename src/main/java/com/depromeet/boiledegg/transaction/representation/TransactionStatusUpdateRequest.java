package com.depromeet.boiledegg.transaction.representation;

import com.depromeet.boiledegg.transaction.domain.TransactionStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class TransactionStatusUpdateRequest {

    private TransactionStatus status;

    @Builder
    public TransactionStatusUpdateRequest(
            final TransactionStatus status
    ) {
        this.status = status;
    }
}
