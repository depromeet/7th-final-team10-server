package com.depromeet.boiledegg.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

import static java.util.Collections.emptyList;

@ToString
@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorStatus {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "-100"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "-101"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "-102"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "-103"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "-104"),
    DISALLOW_SORT_CRITERIA(HttpStatus.BAD_REQUEST, "-105"),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "-200"),

    BOOKSTORE_NOT_FOUND(HttpStatus.NOT_FOUND, "-300"),

    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "-400"),
    INVALID_ISBN(HttpStatus.BAD_REQUEST, "-401"),

    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "-500"),
    CANNOT_CHANGE_TRANSACTION_STATUS(HttpStatus.BAD_REQUEST, "-501"),
    CANNOT_BORROW_MY_BOOK(HttpStatus.BAD_REQUEST, "-502"),
    BOOK_ALREADY_BORROW(HttpStatus.BAD_REQUEST, "-503"),
    BOOK_ALREADY_WAIT(HttpStatus.BAD_REQUEST, "-504"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "-600"),

    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "-700"),
    NOTIFICATION_ALREADY_CHECKED(HttpStatus.BAD_REQUEST, "-701"),
    ;

    private final HttpStatus status;

    private final String code;

    @Override
    public String getMessage() {
        return name();
    }

    @Override
    public List<FieldError> getFieldErrors() {
        return emptyList();
    }
}
