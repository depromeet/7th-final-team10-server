package com.depromeet.boiledegg.security.presentation.controller;


import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Oauth2ControllerTest extends ControllerSupport {

    @Test
    void naver() throws Exception {
        mvc.perform(get("/oauth2/authorization/naver"))
                .andExpect(status().isFound())
                .andDo(
                        document(
                                "oauth2/naver",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @Test
    void google() throws Exception {
        mvc.perform(get("/oauth2/authorization/google"))
                .andExpect(status().isFound())
                .andDo(
                        document(
                                "oauth2/google",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @Test
    void kakao() throws Exception {
        mvc.perform(get("/oauth2/authorization/kakao"))
                .andExpect(status().isFound())
                .andDo(
                        document(
                                "oauth2/kakao",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}
