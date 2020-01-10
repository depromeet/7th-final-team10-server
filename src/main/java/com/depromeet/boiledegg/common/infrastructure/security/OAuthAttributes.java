package com.depromeet.boiledegg.common.infrastructure.security;

import com.depromeet.boiledegg.user.domain.AuthProvider;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@EqualsAndHashCode
@ToString
@Getter
final class OAuthAttributes {

    private final Map<String, Object> attributes;

    private final String nameAttributeKey;

    private final String name;

    private final String email;

    private final AuthProvider authProvider;

    private final String picture;

    @Builder
    public OAuthAttributes(
            final Map<String, Object> attributes,
            final String nameAttributeKey,
            final String name,
            final String email,
            final AuthProvider authProvider,
            final String picture
    ) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.authProvider = authProvider;
        this.picture = picture;
    }
}
