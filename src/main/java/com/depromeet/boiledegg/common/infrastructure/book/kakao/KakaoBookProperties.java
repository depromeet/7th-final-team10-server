package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "boiled-egg.book.kakao")
final class KakaoBookProperties {

    private String url;

    private String apiKey;

    private String authorizationHeaderKey;

    private String authorizationScheme;

    private Client client;

    @Data
    static final class Client {

        private Duration readTimeout;

        private Duration connectionTimeout;
    }
}
