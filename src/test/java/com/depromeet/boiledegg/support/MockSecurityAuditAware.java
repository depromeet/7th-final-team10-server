package com.depromeet.boiledegg.support;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Profile("test")
@Component
public class MockSecurityAuditAware implements AuditorAware<Long> {

    @NonNull
    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(MockSessionUserHolder.getMockSessionUser().getId());
    }
}
