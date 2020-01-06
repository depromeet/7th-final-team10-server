package com.depromeet.boiledegg.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(final String message) {
        super(message);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseRuntimeException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }

    public abstract ErrorStatus getErrorStatus();
}
