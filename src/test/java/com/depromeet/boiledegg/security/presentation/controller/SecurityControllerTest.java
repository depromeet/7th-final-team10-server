package com.depromeet.boiledegg.security.presentation.controller;

import com.depromeet.boiledegg.user.domain.Role;
import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SecurityControllerTest extends ControllerSupport {

    @WithCustomMockUser(roles = Role.ADMIN)
    @Test
    void onlyAdmin_admin() throws Exception {
        mvc.perform(get("/only-admin"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "security/only-admin/admin",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void onlyAdmin_user() throws Exception {
        mvc.perform(get("/only-admin"))
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "security/only-admin/user",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @Test
    void onlyAdmin_notLogin() throws Exception {
        mvc.perform(get("/only-admin"))
                .andExpect(status().isUnauthorized())
                .andDo(
                        document(
                                "security/only-admin/not-login",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser(roles = Role.ADMIN)
    @Test
    void onlyUser_admin() throws Exception {
        mvc.perform(get("/only-user"))
                .andExpect(status().isForbidden())
                .andDo(
                        document(
                                "security/only-user/admin",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void onlyUser_user() throws Exception {
        mvc.perform(get("/only-user"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "security/only-user/user",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @Test
    void onlyUser_notLogin() throws Exception {
        mvc.perform(get("/only-user"))
                .andExpect(status().isUnauthorized())
                .andDo(
                        document(
                                "security/only-user/not-login",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser(roles = Role.ADMIN)
    @Test
    void public_admin() throws Exception {
        mvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "security/public/admin",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void public_user() throws Exception {
        mvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "security/public/user",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @Test
    void public_notLogin() throws Exception {
        mvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "security/public/not-login",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser(roles = Role.ADMIN)
    @Test
    void normal_admin() throws Exception {
        mvc.perform(get("/normal"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "security/normal/admin",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void normal_user() throws Exception {
        mvc.perform(get("/normal"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "security/normal/user",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @Test
    void normal_notLogin() throws Exception {
        mvc.perform(get("/normal"))
                .andExpect(status().isUnauthorized())
                .andDo(
                        document(
                                "security/normal/not-login",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}