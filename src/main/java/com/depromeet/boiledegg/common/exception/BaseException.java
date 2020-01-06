package com.depromeet.boiledegg.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseException extends Exception {

    public BaseException(final String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }

    public abstract ErrorStatus getErrorStatus();
}
