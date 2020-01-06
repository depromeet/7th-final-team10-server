package com.depromeet.boiledegg.common.infrastructure.error.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
final class ExceptionFilter extends OncePerRequestFilter {

    private static final int ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final String ERROR_MESSAGE = "{" +
            "\"message\":\"Internal Server Error\"," +
            "\"status\":500," +
            "\"code\":\"-100\"," +
            "\"fieldErrors\":[]" +
            "}";

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws IOException {
        try {
            filterChain.doFilter(
                    request,
                    response
            );
        } catch (final Throwable throwable) {
            log.error("UncaughtException ", throwable);
            recovery(response);
        }
    }

    private void recovery(final HttpServletResponse response) throws IOException {
        response.setStatus(ERROR_STATUS);

        final var writer = response.getWriter();
        writer.write(ERROR_MESSAGE);
    }
}
