package com.depromeet.boiledegg.support;

import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class MockSessionUserHolder {

    private final SessionUser defaultSessionUser = SessionUser.builder()
            .id(1L)
            .email("jaeyeonling@gmail.com")
            .name("Jaeyeon Kim")
            .build();

    private SessionUser sessionUser = defaultSessionUser;

    public SessionUser getMockSessionUser() {
        return sessionUser;
    }

    public void reset() {
        sessionUser = defaultSessionUser;
    }

    public void set(final SessionUser sessionUser) {
        MockSessionUserHolder.sessionUser = sessionUser;
    }

    public void changeId(final long id) {
        set(SessionUser.builder()
                .id(id)
                .name(sessionUser.getName())
                .email(sessionUser.getEmail())
                .picture(sessionUser.getPicture())
                .build());
    }
}
