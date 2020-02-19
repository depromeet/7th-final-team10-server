package com.depromeet.boiledegg.notification.domain;

import com.depromeet.boiledegg.notification.application.NotificationEventListener;
import com.depromeet.boiledegg.notification.application.NotificationService;
import com.depromeet.boiledegg.notification.domain.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class NotificationEventSaveListener implements NotificationEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    @Override
    public void listen(final Notification notification) {
        notificationService.save(notification);
    }
}
