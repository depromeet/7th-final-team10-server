package com.depromeet.boiledegg.transaction.exception;

import com.depromeet.boiledegg.common.exception.BaseRuntimeException;
import com.depromeet.boiledegg.common.exception.ErrorCode;
import com.depromeet.boiledegg.common.exception.ErrorStatus;

public final class TransactionNotFoundException extends BaseRuntimeException {

    @Override
    public ErrorStatus getErrorStatus() {
        return ErrorCode.TRANSACTION_NOT_FOUND;
    }
}
