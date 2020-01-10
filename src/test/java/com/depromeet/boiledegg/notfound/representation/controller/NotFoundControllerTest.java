package com.depromeet.boiledegg.notfound.representation.controller;

import com.depromeet.boiledegg.user.domain.Role;
import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotFoundControllerTest extends ControllerSupport {

    @Test
    void notFound_notLogin() throws Exception {
        mvc.perform(get("/not-found"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andDo(
                        document(
                                "default/not-found_not-login",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @Test
    @WithCustomMockUser(roles = Role.ADMIN)
    void notFound_login() throws Exception {
        mvc.perform(get("/not-found"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "default/not-found_login",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}