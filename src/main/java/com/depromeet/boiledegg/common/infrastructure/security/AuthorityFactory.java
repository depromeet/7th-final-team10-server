package com.depromeet.boiledegg.common.infrastructure.security;

import com.depromeet.boiledegg.user.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

@Component
public final class AuthorityFactory {

    public Set<GrantedAuthority> create(final Role... roles) {
        return Arrays.stream(roles)
                .map(Role::getRole)
                .map(SimpleGrantedAuthority::new)
                .collect(toUnmodifiableSet());
    }
}
