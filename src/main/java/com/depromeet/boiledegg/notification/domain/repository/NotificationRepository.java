package com.depromeet.boiledegg.notification.domain.repository;

import com.depromeet.boiledegg.notification.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findByToUserIdAndCheckedIsFalse(final Long toUserId);

    Page<Notification> findAllByToUserIdAndCheckedIsFalse(
            final Long toUserId,
            final Pageable pageable
    );
}
