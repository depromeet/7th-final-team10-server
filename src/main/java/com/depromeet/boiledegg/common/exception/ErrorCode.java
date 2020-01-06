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

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "-200"),

    BOOKSTORE_NOT_FOUND(HttpStatus.NOT_FOUND, "-300"),

    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "-400"),
    INVALID_ISBN(HttpStatus.BAD_REQUEST, "-401"),

    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, "-500"),
    CANNOT_CHANGE_TRANSACTION_STATUS(HttpStatus.BAD_REQUEST, "-501"),
    CANNOT_BORROW_MY_BOOK(HttpStatus.BAD_REQUEST, "-502"),
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
