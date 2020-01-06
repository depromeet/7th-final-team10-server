package com.depromeet.boiledegg.common.infrastructure.email;

@FunctionalInterface
public interface EmailClient {

    EmailMessageId<?> send(final MailItem mailItem) throws EmailSendFailException;
}
