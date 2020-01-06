package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import lombok.Data;

@Data(staticConstructor = "of")
final class AuthenticationValue {

    private static final String FORMAT = "%s %s";

    private final String scheme;

    private final String token;

    public String toValue() {
        return String.format(FORMAT, scheme, token);
    }
}
