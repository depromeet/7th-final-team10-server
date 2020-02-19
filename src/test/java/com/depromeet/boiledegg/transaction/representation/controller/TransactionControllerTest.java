package com.depromeet.boiledegg.transaction.representation.controller;

import com.depromeet.boiledegg.book.application.service.BookService;
import com.depromeet.boiledegg.book.representation.representation.MockBookSaveRequestFactory;
import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.MockSessionUserHolder;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import com.depromeet.boiledegg.transaction.representation.TransactionSaveRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest extends ControllerSupport {

    private static final long BOOK_OWNER_ID = 1_000L;

    private TransactionSaveRequest request;

    @BeforeEach
    void setUp(@Autowired final BookService bookService) {
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        final var book = bookService.save(MockBookSaveRequestFactory.create());
        MockSessionUserHolder.reset();

        request = TransactionSaveRequest.builder()
                .bookId(book.getId())
                .build();
    }

    @WithCustomMockUser
    @Test
    void findAll() throws Exception {
        // given
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/find-all",
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
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/transactions?page=0&size=10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/find-all-paging",
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
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/transactions?direction=ASC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/find-all-sort-asc",
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
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/transactions?direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/find-all-sort-desc",
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
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/transactions?page=0&size=10&direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/find-all-paging-with-sort",
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
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // when / then
        mvc.perform(get(location))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/find-by-id",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById_notFound() throws Exception {
        mvc.perform(get("/transactions/78654"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "transactions/find-by-id-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void save() throws Exception {
        // when / then
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        document(
                                "transactions/save",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void save_bookNotFound() throws Exception {
        // given
        final var request = TransactionSaveRequest.builder()
                .bookId(21124235L)
                .build();

        // when / then
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "transactions/save-book-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void save_bookAlreadyBorrowed() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();

        // when / then
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "transactions/save-book-already-borrowed",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void reject() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/reject",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void reject_notFound() throws Exception {
        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put("/transactions/12334124/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "transactions/reject-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void reject_alreadyReject() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        mvc.perform(put(location + "/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "transactions/reject-already-reject",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void reject_notMine() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // when / then
        mvc.perform(put(location + "/reject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "transactions/reject-not-mine",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void confirm() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void confirm_notFound() throws Exception {
        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put("/transactions/12334124/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "transactions/confirm-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void confirm_notWait() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        // when / then
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "transactions/confirm-not-wait",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void confirm_notMine() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // when / then
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "transactions/confirm-not-mine",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void cancel() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());
        MockSessionUserHolder.reset();

        // when / then
        mvc.perform(put(location + "/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/cancel",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void cancel_notFound() throws Exception {
        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put("/transactions/12334124/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "transactions/cancel-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void cancel_alreadyWait() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());
        MockSessionUserHolder.reset();

        mvc.perform(put(location + "/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        // when / then
        mvc.perform(put(location + "/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "transactions/cancel-already-wait",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void cancel_notMine() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/cancel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "transactions/cancel-not-mine",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void returns() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();

        // when / then
        mvc.perform(put(location + "/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/return",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void return_notFound() throws Exception {
        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put("/transactions/12334124/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "transactions/return-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void returns_notBorrowed() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();

        mvc.perform(put(location + "/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        // when / then
        mvc.perform(put(location + "/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "transactions/return-not-borrowed",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void returns_notMine() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );

        // when / then
        mvc.perform(put(location + "/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "transactions/return-not-mine",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void take() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();

        mvc.perform(put(location + "/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/return",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );

        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/take")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/take",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void take_notFound() throws Exception {
        // when / then
        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put("/transactions/12334124/take")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "transactions/take-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void take_notReturn() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();

        mvc.perform(put(location + "/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/return",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/take")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        // when / then
        mvc.perform(put(location + "/take")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "transactions/take-not-return",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();
    }

    @WithCustomMockUser
    @Test
    void take_notMine() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(BOOK_OWNER_ID);
        mvc.perform(put(location + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/confirm",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
        MockSessionUserHolder.reset();

        mvc.perform(put(location + "/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "transactions/return",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );

        // when / then
        mvc.perform(put(location + "/take")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "transactions/take-not-mine",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}