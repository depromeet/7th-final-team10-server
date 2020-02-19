package com.depromeet.boiledegg.notification.application;

import com.depromeet.boiledegg.notification.domain.entity.Notification;

public interface NotificationEventListener {

    void listen(final Notification notification);
}
