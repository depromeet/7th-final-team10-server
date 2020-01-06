package com.depromeet.boiledegg.common.infrastructure.book;

import com.depromeet.boiledegg.common.exception.BaseException;
import com.depromeet.boiledegg.common.exception.ErrorCode;
import com.depromeet.boiledegg.common.exception.ErrorStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BookSearchFailException extends BaseException {

    public BookSearchFailException(final Throwable cause) {
        super(cause);
    }

    @Override
    public ErrorStatus getErrorStatus() {
        return ErrorCode.INTERNAL_SERVER_ERROR;
    }
}
