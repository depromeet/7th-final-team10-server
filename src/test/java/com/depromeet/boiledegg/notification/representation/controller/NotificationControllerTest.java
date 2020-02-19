package com.depromeet.boiledegg.notification.representation.controller;

import com.depromeet.boiledegg.notification.domain.NotificationEvent;
import com.depromeet.boiledegg.notification.domain.entity.Notification;
import com.depromeet.boiledegg.notification.domain.repository.NotificationRepository;
import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.MockSessionUserHolder;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotificationControllerTest extends ControllerSupport {

    private static final long ANOTHER_USER_ID = 1_000L;

    @Autowired
    private NotificationRepository notificationRepository;

    @WithCustomMockUser
    @Test
    void findAll() throws Exception {
        // given
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());

        // when / then
        mvc.perform(get("/notifications"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "notifications/find-all",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAll_paging() throws Exception {
        // given
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.CHANGE_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());

        // when / then
        mvc.perform(get("/notifications?page=0&size=10"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "notifications/find-all-paging",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAll_sortAsc() throws Exception {
        // given
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.CHANGE_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());


        // when / then
        mvc.perform(get("/notifications?direction=ASC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "notifications/find-all-sort-asc",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAll_sortDesc() throws Exception {
        // given
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.CHANGE_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());


        // when / then
        mvc.perform(get("/notifications?direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "notifications/find-all-sort-desc",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAll_pagingWithSort() throws Exception {
        // given
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());
        notificationRepository.save(Notification.builder()
                .event(NotificationEvent.CHANGE_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());


        // when / then
        mvc.perform(get("/notifications?page=0&size=10&direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "notifications/find-all-paging-with-sort",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById() throws Exception {
        // given
        final var notification = notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());

        // when / then
        mvc.perform(get("/notifications/" + notification.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "notifications/find-by-id",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById_notFound() throws Exception {
        // when / then
        mvc.perform(get("/notifications/78654"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "notifications/find-by-id-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void check() throws Exception {
        // given
        final var notification = notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());

        // when / then
        mvc.perform(post("/notifications/" + notification.getId() + "/check"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "notifications/check",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void check_notFound() throws Exception {
        mvc.perform(post("/notifications/2363524/check"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "notifications/check-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void check_notMine() throws Exception {
        // given
        final var notification = notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(ANOTHER_USER_ID)
                .fromUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .build());

        // when / then
        mvc.perform(post("/notifications/" + notification.getId() + "/check"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "notifications/check-not-mine",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void check_alreadyChecked() throws Exception {
        // given
        final var notification = notificationRepository.save(Notification.builder()
                .event(NotificationEvent.START_BOOK_TRANSACTION)
                .toUserId(MockSessionUserHolder.getMockSessionUser().getId())
                .fromUserId(ANOTHER_USER_ID)
                .build());

        mvc.perform(post("/notifications/" + notification.getId() + "/check"))
                .andExpect(status().isOk());

        // when / then
        mvc.perform(post("/notifications/" + notification.getId() + "/check"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "notifications/check-already-checked",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}