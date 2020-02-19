package com.depromeet.boiledegg.notification.application;

import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.notification.domain.entity.Notification;
import com.depromeet.boiledegg.notification.domain.repository.NotificationRepository;
import com.depromeet.boiledegg.notification.exception.NotificationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository repository;

    public Notification save(final Notification notification) {
        return repository.save(notification);
    }

    @Transactional(readOnly = true)
    public Optional<Notification> findById(final Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Notification> findAll(
            final SessionUser user,
            final Pageable pageable
    ) {
        return repository.findAllByToUserIdAndCheckedIsFalse(
                user.getId(),
                pageable
        );
    }

    @Transactional
    public void check(
            final Long id,
            final SessionUser user
    ) {
        findOrThrow(id).check(user);
    }

    private Notification findOrThrow(final Long id) {
        return findById(id).orElseThrow(NotificationNotFoundException::new);
    }
}
