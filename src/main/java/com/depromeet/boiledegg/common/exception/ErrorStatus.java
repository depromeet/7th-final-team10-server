package com.depromeet.boiledegg.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public interface ErrorStatus {

    HttpStatus getStatus();

    String getCode();

    String getMessage();

    List<FieldError> getFieldErrors();
}
