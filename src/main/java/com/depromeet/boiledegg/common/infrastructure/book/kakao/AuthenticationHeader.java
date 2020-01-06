package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import lombok.Data;

@Data(staticConstructor = "of")
final class AuthenticationHeader {

    private final String key;

    private final AuthenticationValue value;

    public String getValue() {
        return value.toValue();
    }
}
