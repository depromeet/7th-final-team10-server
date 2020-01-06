package com.depromeet.boiledegg.transaction.domain;

import com.depromeet.boiledegg.transaction.exception.CannotChangeTransactionStatusException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public enum TransactionStatus {

    COMPLETED,
    RETURN(TransactionStatus.COMPLETED),
    CANCEL,
    REJECT,
    BORROWED(
            TransactionStatus.CANCEL,
            TransactionStatus.RETURN
    ),
    ASK_TO_BORROW(
            TransactionStatus.BORROWED,
            TransactionStatus.REJECT
    ),
    ;

    public static final TransactionStatus DEFAULT = ASK_TO_BORROW;

    private final List<TransactionStatus> nextCandidates;

    TransactionStatus(final TransactionStatus... statuses) {
        this(List.of(statuses));
    }

    public TransactionStatus change(final TransactionStatus status) {
        if (cannotChange(status)) {
            log.debug("Cannot change [before={}, after={}, candidates={}]", this, status, nextCandidates);
            throw new CannotChangeTransactionStatusException();
        }

        return status;
    }

    private boolean canChange(final TransactionStatus status) {
        return nextCandidates.contains(status);
    }

    private boolean cannotChange(final TransactionStatus status) {
        return !canChange(status);
    }
}