package com.depromeet.boiledegg.common.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Profile("!test")
@Component
final class LoginUserArgumentResolver implements SecurityUserArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        final var isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        final var isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(
            @NonNull final MethodParameter ignoredMethodParameter,
            final ModelAndViewContainer ignoredModelAndViewContainer,
            @NonNull final NativeWebRequest ignoredNativeWebRequest,
            final WebDataBinderFactory ignoredWebDataBinderFactory
    ) {
        return httpSession.getAttribute(SessionUser.ATTRIBUTE_KEY);
    }
}
