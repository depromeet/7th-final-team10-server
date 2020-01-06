package com.depromeet.boiledegg.support;

import com.depromeet.boiledegg.common.infrastructure.security.AuthorityFactory;
import com.depromeet.boiledegg.common.utils.random.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    private final AuthorityFactory authorityFactory;

    @Override
    public SecurityContext createSecurityContext(final WithCustomMockUser customUser) {
        final var securityContext = SecurityContextHolder.createEmptyContext();

        final var principal = new User(
                RandomString.getSecureString(10),
                RandomString.getSecureString(10),
                true,
                true,
                true,
                true,
                authorityFactory.create(customUser.roles())
        );
        final var authentication = new UsernamePasswordAuthenticationToken(
                principal,
                principal.getPassword(),
                principal.getAuthorities()
        );
        securityContext.setAuthentication(authentication);

        return securityContext;
    }
}
