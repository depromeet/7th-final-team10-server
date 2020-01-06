package com.depromeet.boiledegg.common.infrastructure.error.handler;

import com.depromeet.boiledegg.common.exception.BaseException;
import com.depromeet.boiledegg.common.exception.BaseRuntimeException;
import com.depromeet.boiledegg.common.exception.ErrorCode;
import com.depromeet.boiledegg.common.exception.ErrorResponse;
import com.depromeet.boiledegg.common.exception.ErrorStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
final class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseRuntimeException.class)
    ResponseEntity<Object> baseRuntimeException(final BaseRuntimeException exception) {
        log.debug("Platform runtime exception ", exception);
        return makeResponse(exception.getErrorStatus());
    }

    @ExceptionHandler(BaseException.class)
    ResponseEntity<Object> baseException(final BaseException exception) {
        log.debug("Platform exception ", exception);
        return makeResponse(exception.getErrorStatus());
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<Object> handleUncaughtException(final Throwable throwable) {
        if (throwable.getCause() instanceof BaseRuntimeException) {
            return baseRuntimeException((BaseRuntimeException) throwable.getCause());
        }
        if (throwable.getCause() instanceof BaseException) {
            return baseException((BaseException) throwable.getCause());
        }

        log.error("Uncaught exception ", throwable);
        return makeResponse(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            @NonNull final NoHandlerFoundException ignoredException,
            @NonNull final HttpHeaders ignoredHeaders,
            @NonNull final HttpStatus ignoredStatus,
            @NonNull final WebRequest ignoredRequest
    ) {
        return makeResponse(ErrorCode.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull final MethodArgumentNotValidException exception,
            @NonNull final HttpHeaders ignoredHeaders,
            @NonNull final HttpStatus ignoredStatus,
            @NonNull final WebRequest ignoredRequest
    ) {
        log.debug("Invalid method arguments ", exception);
        final var status = MethodArgumentNotValidStatus.of(exception.getBindingResult());

        return makeResponse(status);
    }

    private ResponseEntity<Object> makeResponse(final ErrorStatus errorStatus) {
        return ResponseEntity.status(errorStatus.getStatus())
                .body(ErrorResponse.of(errorStatus));
    }
}
