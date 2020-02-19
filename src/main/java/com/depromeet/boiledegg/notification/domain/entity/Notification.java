package com.depromeet.boiledegg.notification.domain.entity;

import com.depromeet.boiledegg.common.domain.entity.base.CreateDateAuditEntity;
import com.depromeet.boiledegg.common.exception.ForbiddenException;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.notification.domain.NotificationEvent;
import com.depromeet.boiledegg.notification.exception.NotificationAlreadyCheckedException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNullElse;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(columnList = "toUserId, checked")
        }
)
@Entity
public class Notification extends CreateDateAuditEntity {

    public static final Long ADMIN_USER_ID = 0L;

    @Getter
    @Column(nullable = false)
    private Long fromUserId;

    @Getter
    @Column(nullable = false)
    private Long toUserId;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationEvent event;

    @Getter
    @Column(nullable = true)
    private LocalDateTime checkedDate;

    @Getter
    @Column(nullable = false)
    private boolean checked;

    @Builder
    public Notification(
            final Long fromUserId,
            final Long toUserId,
            final NotificationEvent event
            ) {
        this.fromUserId = requireNonNullElse(
                fromUserId,
                ADMIN_USER_ID
        );
        this.toUserId = toUserId;
        this.event = event;
    }

    public void check(final SessionUser user) {
        if (user.mismatchUserId(toUserId)) {
            throw new ForbiddenException();
        }
        if (checked) {
            throw new NotificationAlreadyCheckedException();
        }

        checkedDate = LocalDateTime.now();
        checked = true;
    }
}
