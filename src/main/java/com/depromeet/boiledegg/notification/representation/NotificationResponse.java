package com.depromeet.boiledegg.notification.representation;

import com.depromeet.boiledegg.notification.domain.NotificationEvent;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponse {

    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private NotificationEvent event;

    private LocalDateTime checkedDate;

    private boolean checked;
}
