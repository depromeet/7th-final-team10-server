package com.depromeet.boiledegg.common.exception;

@FunctionalInterface
public interface BaseRuntimeExceptionFactory {

    BaseRuntimeException create(final ErrorStatus errorStatus);
}
