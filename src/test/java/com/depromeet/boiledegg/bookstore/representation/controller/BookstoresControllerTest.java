package com.depromeet.boiledegg.bookstore.representation.controller;

import com.depromeet.boiledegg.bookstore.representation.BookstoreSaveRequest;
import com.depromeet.boiledegg.common.domain.Coordinate;
import com.depromeet.boiledegg.common.domain.Location;
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

class BookstoresControllerTest extends ControllerSupport {

    private BookstoreSaveRequest bookstoreSaveRequest;

    @BeforeEach
    void setUp() {
        final var coordinate = Coordinate.builder()
                .latitude(37.497908)
                .longitude(127.026511)
                .build();
        final var location = Location.builder()
                .coordinate(coordinate)
                .detail("메가박스 건물 앞")
                .build();

        bookstoreSaveRequest = BookstoreSaveRequest.builder()
                .name("메가박스 책방")
                .description("메가박스 앞 책방입니당!")
                .location(location)
                .build();
    }

    @WithCustomMockUser
    @Test
    void save() throws Exception {
        // when / then
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(
                        document(
                                "bookstores/save",
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
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/bookstores"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-all",
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
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/bookstores?page=0&size=10"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-all-paging",
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
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/bookstores?direction=ASC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-all-sort-asc",
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
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/bookstores?direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-all-sort-desc",
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
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/bookstores?direction=ASC&sortCriteria=likeCount"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-all-sort-like-asc",
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
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated());

        // when / then
        mvc.perform(get("/bookstores?direction=DESC&sortCriteria=likeCount"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-all-sort-like-desc",
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
        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated());

        mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)));

        // when / then
        mvc.perform(get("/bookstores?page=0&size=10&direction=DESC&sortCriteria=id"))
                .andDo(log())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-all-paging-with-sort",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById() throws Exception {
        final var location = mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        mvc.perform(get(location))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/find-by-id",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById_notFound() throws Exception {
        mvc.perform(get("/bookstores/78654"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "bookstores/find-by-id-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void like() throws Exception {
        final var location = mvc.perform(post("/bookstores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(bookstoreSaveRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        mvc.perform(post(location + "/like"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "bookstores/like",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void like_notFound() throws Exception {
        mvc.perform(post("/bookstores/2363524/like"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "bookstores/like-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}