package com.depromeet.boiledegg.notification.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotificationMessage {

    private final String message;

    public static NotificationMessage of(final String message) {
        return new NotificationMessage(message);
    }

    public NotificationMessage change(final NotificationMessageParameter parameter) {
        return of(parameter.replace(toString()));
    }

    @Override
    public String toString() {
        return message;
    }
}
