package com.depromeet.boiledegg.common.infrastructure.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    private static final int DEFAULT_ERROR_STATUS = HttpStatus.UNAUTHORIZED.value();
    private static final String DEFAULT_ERROR_MESSAGE = "{" +
            "\"message\":\"Unauthorized\"," +
            "\"status\":401," +
            "\"code\":\"-100\"," +
            "\"fieldErrors\":[]" +
            "}";

    @Override
    public void onAuthenticationFailure(
            final HttpServletRequest ignoredRequest,
            final HttpServletResponse response,
            final AuthenticationException ignoredException
    ) throws IOException {
        response.setStatus(DEFAULT_ERROR_STATUS);

        final var writer = response.getWriter();
        writer.write(DEFAULT_ERROR_MESSAGE);
    }
}