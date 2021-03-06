package com.depromeet.boiledegg.book.representation.representation.controller;

import com.depromeet.boiledegg.book.representation.BookSaveRequest;
import com.depromeet.boiledegg.book.representation.representation.MockBookSaveRequestFactory;
import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends ControllerSupport {

    private BookSaveRequest bookSaveRequest;

    @BeforeEach
    void setUp() {
        bookSaveRequest = MockBookSaveRequestFactory.create();
    }

    @WithCustomMockUser
    @Test
    void save() throws Exception {
        // when / then
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        document(
                                "books/save",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void save_invalidIsbn() throws Exception {
        // when / then
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(BookSaveRequest.builder()
                        .isbn("1234567890")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(
                        document(
                                "books/save-invalid-isbn",
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
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/books"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-all",
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
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/books?page=0&size=10"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-all-paging",
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
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/books?direction=ASC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-all-sort-asc",
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
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/books?direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-all-sort-desc",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAll_sortAscLike() throws Exception {
        // given
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/books?direction=ASC&sortCriteria=likeCount"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-all-sort-like-asc",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAll_sortDescLike() throws Exception {
        // given
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/books?direction=DESC&sortCriteria=likeCount"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-all-sort-like-desc",
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
        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated());

        mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)));

        // when / then
        mvc.perform(get("/books?page=0&size=10&direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-all-paging-with-sort",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById() throws Exception {
        final var location = mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        mvc.perform(get(location))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/find-by-id",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById_notFound() throws Exception {
        mvc.perform(get("/books/78654"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "books/find-by-id-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void like() throws Exception {
        final var location = mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        mvc.perform(post(location + "/like"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/like",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void like_notFound() throws Exception {
        mvc.perform(post("/books/2363524/like"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "books/like-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void search_byTitle() throws Exception {
        mvc.perform(get("/books/search?type=TITLE&value=Hello"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/search-by-title",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void search_byIsbn() throws Exception {
        mvc.perform(get("/books/search?type=ISBN&value=9788964150290"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/search-by-isbn",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void searchByBook() throws Exception {
        final var location = mvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookSaveRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        mvc.perform(get(location + "/search"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "books/search-by-book",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}