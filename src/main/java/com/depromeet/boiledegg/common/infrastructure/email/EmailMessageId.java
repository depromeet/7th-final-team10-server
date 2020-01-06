package com.depromeet.boiledegg.common.infrastructure.email;

@FunctionalInterface
public interface EmailMessageId<T> {

    T getValue();
}
