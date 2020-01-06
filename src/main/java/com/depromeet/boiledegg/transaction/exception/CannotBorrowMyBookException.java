package com.depromeet.boiledegg.transaction.exception;

import com.depromeet.boiledegg.common.exception.BaseRuntimeException;
import com.depromeet.boiledegg.common.exception.ErrorCode;
import com.depromeet.boiledegg.common.exception.ErrorStatus;

public final class CannotBorrowMyBookException extends BaseRuntimeException {

    @Override
    public ErrorStatus getErrorStatus() {
        return ErrorCode.CANNOT_BORROW_MY_BOOK;
    }
}
