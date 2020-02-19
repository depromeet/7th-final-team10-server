package com.depromeet.boiledegg.notfound.representation.controller;

import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NotFoundControllerTest extends ControllerSupport {

    @Test
    void notFound() throws Exception {
        mvc.perform(get("/not-found"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "default/not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}