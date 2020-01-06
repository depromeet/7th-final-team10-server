package com.depromeet.boiledegg.transaction.representation.controller;

import com.depromeet.boiledegg.book.application.service.BookService;
import com.depromeet.boiledegg.book.representation.representation.MockBookSaveRequestFactory;
import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.MockSessionUserHolder;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import com.depromeet.boiledegg.transaction.representation.TransactionSaveRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionControllerTest extends ControllerSupport {

    private static final long bookOwnerId = 1000L;

    private TransactionSaveRequest request;

    @BeforeEach
    void setUp(@Autowired final BookService bookService) {
        // given
        MockSessionUserHolder.changeId(bookOwnerId);
        final var book = bookService.save(MockBookSaveRequestFactory.create());
        MockSessionUserHolder.reset();

        request = TransactionSaveRequest.builder()
                .bookId(book.getId())
                .build();
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
        mvc.perform(get(location)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
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
        MockSessionUserHolder.changeId(bookOwnerId);
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
        MockSessionUserHolder.changeId(bookOwnerId);
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
    void cancel() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(bookOwnerId);
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
    void returns() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(bookOwnerId);
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
    void take() throws Exception {
        // given
        final var location = mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        MockSessionUserHolder.changeId(bookOwnerId);
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
        MockSessionUserHolder.changeId(bookOwnerId);
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
}