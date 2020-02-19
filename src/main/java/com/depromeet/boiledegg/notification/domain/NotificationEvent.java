package com.depromeet.boiledegg.notification.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationEvent {

    START_BOOK_TRANSACTION("${FROM}"),
    CHANGE_BOOK_TRANSACTION("TODO");

    private final String messageTemplate;

    public String makeMessage(final NotificationMessageParameter... parameters) {
        var message = NotificationMessage.of(messageTemplate);
        for (final var parameter : parameters) {
            message = message.change(parameter);
        }

        return message.toString();
    }
}
