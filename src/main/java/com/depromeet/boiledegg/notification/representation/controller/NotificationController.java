package com.depromeet.boiledegg.notification.representation.controller;

import com.depromeet.boiledegg.common.infrastructure.security.LoginUser;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.common.representation.PageRequest;
import com.depromeet.boiledegg.notification.application.NotificationService;
import com.depromeet.boiledegg.notification.exception.NotificationNotFoundException;
import com.depromeet.boiledegg.notification.representation.NotificationResponse;
import com.depromeet.boiledegg.notification.representation.assembler.NotificationResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(NotificationController.BASE_PATH)
public class NotificationController {

    static final String BASE_PATH = "/notifications";

    private final NotificationService notificationService;

    private final NotificationResponseAssembler assembler;

    @Secured("ROLE_USER")
    @GetMapping
    Page<NotificationResponse> findAll(
            @LoginUser final SessionUser user,
            final PageRequest pageRequest
    ) {
        final var notifications = notificationService.findAll(
                user,
                pageRequest
        );

        return notifications.map(assembler::mapFrom);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    NotificationResponse findById(@PathVariable final Long id) {
        return notificationService.findById(id)
                .map(assembler::mapFrom)
                .orElseThrow(NotificationNotFoundException::new);
    }

    @Secured("ROLE_USER")
    @PostMapping("/{id}/check")
    void check(
            @PathVariable final Long id,
            @LoginUser final SessionUser user
    ) {
        notificationService.check(
                id,
                user
        );
    }
}

