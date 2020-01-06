package com.depromeet.boiledegg.common.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@Getter
public final class ErrorResponse {

    private final String message;

    private final int status;

    private final String code;

    private final List<FieldError> fieldErrors;

    @Builder(access = AccessLevel.PRIVATE)
    private ErrorResponse(
            final String message,
            final int status,
            final String code,
            final List<FieldError> fieldErrors
    ) {
        this.message = message;
        this.status = status;
        this.code = code;
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(final ErrorStatus errorStatus) {
        return ErrorResponse.builder()
                .message(errorStatus.getMessage())
                .status(errorStatus.getStatus().value())
                .code(errorStatus.getCode())
                .fieldErrors(errorStatus.getFieldErrors())
                .build();
    }
}
