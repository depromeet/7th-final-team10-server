package com.depromeet.boiledegg.common.infrastructure.error.handler;

import com.depromeet.boiledegg.common.exception.ErrorCode;
import com.depromeet.boiledegg.common.exception.ErrorStatus;
import com.depromeet.boiledegg.common.exception.FieldError;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class MethodArgumentNotValidStatus implements ErrorStatus {

    private static final ErrorCode ERROR_CODE = ErrorCode.BAD_REQUEST;

    private final List<FieldError> fieldErrors;

    public static MethodArgumentNotValidStatus of(final BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::of)
                .collect(collectingAndThen(toList(), MethodArgumentNotValidStatus::new));
    }

    @Override
    public HttpStatus getStatus() {
        return ERROR_CODE.getStatus();
    }

    @Override
    public String getCode() {
        return ERROR_CODE.getCode();
    }

    @Override
    public String getMessage() {
        return ERROR_CODE.name();
    }

    @Override
    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
