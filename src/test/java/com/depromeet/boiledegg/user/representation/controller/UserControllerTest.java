package com.depromeet.boiledegg.user.representation.controller;

import com.depromeet.boiledegg.common.utils.random.RandomString;
import com.depromeet.boiledegg.support.ApiDocumentUtil;
import com.depromeet.boiledegg.support.ControllerSupport;
import com.depromeet.boiledegg.support.WithCustomMockUser;
import com.depromeet.boiledegg.user.domain.AuthProvider;
import com.depromeet.boiledegg.user.domain.Role;
import com.depromeet.boiledegg.user.domain.entity.User;
import com.depromeet.boiledegg.user.domain.repository.UserRepository;
import com.depromeet.boiledegg.user.representation.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Objects;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerSupport {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .nickname("Nickname")
                .name("Name")
                .picture("https://Picture")
                .authProvider(AuthProvider.KAKAO)
                .email(RandomString.getSecureString(20) + "@gmail.com")
                .role(Role.USER)
                .build());
    }

    @WithCustomMockUser
    @Test
    void findMe() throws Exception {
        mvc.perform(get("/users/me"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document(
                                "user/find-me",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void updateMe() throws Exception {
        // given
        final var updateRequest = UserUpdateRequest.builder()
                .nickname("ChangeNickname")
                .name("ChangeName")
                .picture("https://ChangePicture")
                .build();

        // when / then
        mvc.perform(put("/users/me")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "user/update-me",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById() throws Exception {
        // when / then
        mvc.perform(get("/users/" + user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "user/find-by-id",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findById_notFound() throws Exception {
        // when / then
        mvc.perform(get("/users/675654"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        document(
                                "user/find-by-id-not-found",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAllBook() throws Exception {
        // when / then
        mvc.perform(get("/users/" + user.getId() + "/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "user/find-all-book",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }

    @WithCustomMockUser
    @Test
    void findAllTransaction() throws Exception {
        // when / then
        mvc.perform(get("/users/" + user.getId() + "/transactions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "user/find-all-transaction",
                                ApiDocumentUtil.request(),
                                ApiDocumentUtil.response(),
                                pathParameters()
                        )
                );
    }
}
