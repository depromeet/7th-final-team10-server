package com.depromeet.boiledegg.post.representation.controller;

import com.depromeet.boiledegg.post.representation.PostSaveRequest;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PostControllerTest extends ControllerSupport {

    @WithCustomMockUser
    @Test
    void save() throws Exception {
        // given
        final var request = PostSaveRequest.builder()
                .title("title")
                .content("content")
                .build();

        // when / then
        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isCreated())

        ;
    }

    @WithCustomMockUser
    @Test
    void findAll() throws Exception {
        mvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }

    @WithCustomMockUser
    @Test
    void findById_notFound() throws Exception {
        mvc.perform(get("/posts/78654"))
                .andExpect(status().isNotFound());
    }

    @WithCustomMockUser
    @Test
    void findById_found() throws Exception {
        // TODO found

        mvc.perform(get("/posts/987654321"))
                .andExpect(status().isNotFound());
    }
}