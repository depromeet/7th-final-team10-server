package com.depromeet.boiledegg.common.infrastructure.security;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public final class SessionUser implements Serializable {

    public static final String ATTRIBUTE_KEY = "User";

    private final Long id;

    private final String name;

    private final String email;

    private final String picture;

    private final String nickname;

    @Builder
    public SessionUser(
            final Long id,
            final String name,
            final String email,
            final String picture,
            final String nickname
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.nickname = nickname;
    }

    public boolean matchUserId(final Long owner) {
        return id.equals(owner);
    }

    public boolean mismatchUserId(final Long owner) {
        return !matchUserId(owner);
    }
}
