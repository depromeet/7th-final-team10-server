package com.depromeet.boiledegg.common.infrastructure.security;

import com.depromeet.boiledegg.user.domain.AuthProvider;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Map;

@AllArgsConstructor
enum Provider {

    NAVER("naver") {

        private static final String RESPONSE_KEY = "response";
        private static final String NAME_KEY = "name";
        private static final String EMAIL_KEY = "email";
        private static final String PICTURE_KEY = "profileImage";
        private static final String NAME_ATTRIBUTE_KEY = "id";

        @Override
        OAuthAttributes parseOAuthAttributes(
                final String ignored,
                final Map<String, Object> attributes
        ) {
            @SuppressWarnings("unchecked")
            final var response = (Map<String, Object>) attributes.get(RESPONSE_KEY);

            return OAuthAttributes.builder()
                    .authProvider(AuthProvider.NAVER)
                    .name((String) response.get(NAME_KEY))
                    .email((String) response.get(EMAIL_KEY))
                    .picture((String) response.get(PICTURE_KEY))
                    .attributes(response)
                    .nameAttributeKey(NAME_ATTRIBUTE_KEY)
                    .build();
        }
    },
    GOOGLE("google") {

        private static final String NAME_KEY = "name";
        private static final String EMAIL_KEY = "email";
        private static final String PICTURE_KEY = "picture";

        @Override
        OAuthAttributes parseOAuthAttributes(
                final String userNameAttributeName,
                final Map<String, Object> attributes
        ) {
            return OAuthAttributes.builder()
                    .authProvider(AuthProvider.GOOGLE)
                    .name((String) attributes.get(NAME_KEY))
                    .email((String) attributes.get(EMAIL_KEY))
                    .picture((String) attributes.get(PICTURE_KEY))
                    .attributes(attributes)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }
    },
    KAKAO("kakao") {

        private static final String PROPERTIES_KEY = "properties";
        private static final String ACCOUNT_KEY = "kakao_account";
        private static final String NAME_KEY = "nickname";
        private static final String EMAIL_KEY = "email";
        private static final String PICTURE_KEY = "profile_image";

        @SuppressWarnings("unchecked")
        @Override
        OAuthAttributes parseOAuthAttributes(
                final String userNameAttributeName,
                final Map<String, Object> attributes
        ) {
            final var properties = (Map<String, Object>) attributes.get(PROPERTIES_KEY);
            final var account = (Map<String, Object>) attributes.get(ACCOUNT_KEY);

            return OAuthAttributes.builder()
                    .authProvider(AuthProvider.KAKAO)
                    .name((String) properties.get(NAME_KEY))
                    .email((String) account.get(EMAIL_KEY))
                    .picture((String) properties.get(PICTURE_KEY))
                    .attributes(attributes)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }
    }
    ;

    private final String registrationId;

    public static Provider of(final String registrationId) {
        // TODO Change custom exception

        return Arrays.stream(values())
                .filter(provider -> provider.matchRegistrationId(registrationId))
                .findAny()
                .orElseThrow();
    }

    private boolean matchRegistrationId(final String registrationId) {
        return this.registrationId.equals(registrationId);
    }

    abstract OAuthAttributes parseOAuthAttributes(
            final String userNameAttributeName,
            final Map<String, Object> attributes
    );
}
