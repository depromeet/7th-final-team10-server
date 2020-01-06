package com.depromeet.boiledegg.common.infrastructure.security;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@UtilityClass
public class SessionUserContextHolder {

    public Optional<SessionUser> getSessionUser() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(HttpServletRequest::getSession)
                .map(httpSession -> (SessionUser) httpSession.getAttribute(SessionUser.ATTRIBUTE_KEY));
    }

    public Optional<Long> getSessionUserId() {
        return getSessionUser().map(SessionUser::getId);
    }
}
