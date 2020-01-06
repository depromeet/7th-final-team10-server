package com.depromeet.boiledegg.common.exception;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@ToString
@EqualsAndHashCode
@Getter
public final class FieldError {

    private static final String BLANK = "";

    private final String field;

    private final String value;

    private final String reason;

    @Builder
    public FieldError(
            final String field,
            final String value,
            final String reason
    ) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    public static FieldError of(
            final org.springframework.validation.FieldError fieldError
    ) {
        return FieldError.builder()
                .field(fieldError.getField())
                .value(parseValue(fieldError))
                .reason(fieldError.getDefaultMessage())
                .build();
    }

    private static String parseValue(
            final org.springframework.validation.FieldError fieldError
    ) {
        return Optional.ofNullable(fieldError.getRejectedValue())
                .map(Object::toString)
                .orElse(BLANK);
    }
}
