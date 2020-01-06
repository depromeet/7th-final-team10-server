package com.depromeet.boiledegg.common.infrastructure.email;

import com.depromeet.boiledegg.common.exception.BaseException;
import com.depromeet.boiledegg.common.exception.ErrorCode;
import com.depromeet.boiledegg.common.exception.ErrorStatus;

public class EmailSendFailException extends BaseException {

    public EmailSendFailException(final Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
