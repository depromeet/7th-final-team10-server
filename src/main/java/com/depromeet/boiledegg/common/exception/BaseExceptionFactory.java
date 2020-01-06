package com.depromeet.boiledegg.common.exception;

@FunctionalInterface
public interface BaseExceptionFactory {

    BaseException create(final ErrorStatus errorStatus);
}
