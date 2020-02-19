package com.depromeet.boiledegg.notification.representation.assembler;

import com.depromeet.boiledegg.notification.domain.entity.Notification;
import com.depromeet.boiledegg.notification.representation.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationResponseAssembler {

    private final ModelMapper mapper;

    public NotificationResponse mapFrom(final Notification notification) {
        return mapper.map(notification, NotificationResponse.class);
    }
}
