package com.depromeet.boiledegg.notification.domain;

import lombok.Builder;

public final class NotificationMessageParameter {

    private final String key;

    private final String value;

    @Builder
    public NotificationMessageParameter(
            final String key,
            final Object value
    ) {
        this.key = key;
        this.value = value.toString();
    }

    public static NotificationMessageParameter fromUserId(final long fromUserId) {
        return NotificationMessageParameter.builder()
                .key("{FROM_USER_ID}")
                .value(fromUserId)
                .build();
    }

    public static NotificationMessageParameter toUserId(final long toUserId) {
        return NotificationMessageParameter.builder()
                .key("{TO_USER_ID}")
                .value(toUserId)
                .build();
    }

    public static NotificationMessageParameter of(
            final String key,
            final Object value
    ) {
        return NotificationMessageParameter.builder()
                .key(key)
                .value(value)
                .build();
    }

    public String replace(final String template) {
        return template.replaceAll(
                key,
                value
        );
    }
}
