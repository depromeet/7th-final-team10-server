package com.depromeet.boiledegg.common.infrastructure.security;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Profile("!test")
@Component
public class SecurityAuditAware implements AuditorAware<Long> {

    @NonNull
    @Override
    public Optional<Long> getCurrentAuditor() {
        return SessionUserContextHolder.getSessionUserId();
    }
}
