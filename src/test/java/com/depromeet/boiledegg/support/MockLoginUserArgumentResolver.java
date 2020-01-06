package com.depromeet.boiledegg.support;

import com.depromeet.boiledegg.common.infrastructure.security.LoginUser;
import com.depromeet.boiledegg.common.infrastructure.security.SecurityUserArgumentResolver;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Profile("test")
@Component
public final class MockLoginUserArgumentResolver implements SecurityUserArgumentResolver {

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
        return MockSessionUserHolder.getMockSessionUser();
    }
}
