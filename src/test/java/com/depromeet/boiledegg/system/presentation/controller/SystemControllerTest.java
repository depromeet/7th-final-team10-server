package com.depromeet.boiledegg.system.presentation.controller;

import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SystemControllerTest extends ControllerSupport {

    @WithCustomMockUser
    @Test
    void currentTimeMillis() throws Exception {
        mvc.perform(get("/current-time-millis"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "system/current-time-millis",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void me() throws Exception {
        mvc.perform(get("/me"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "system/me",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}