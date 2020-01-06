package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import com.depromeet.boiledegg.common.infrastructure.book.BookClient;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchFailException;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchOption;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchResponse;
import com.depromeet.boiledegg.common.infrastructure.cache.CacheKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.util.stream.Collectors.toUnmodifiableList;

@Slf4j
@Component
class KakaoBookClient implements BookClient {

    private static final String TARGET_KEY = "target";
    private static final String SEARCH_KEY = "query";

    private final ObjectMapper objectMapper;

    private final HttpClient client;

    private final RestTemplate client2;

    private final URI baseUri;

    private final AuthenticationHeader authenticationHeader;

    private final KakaoBookAssembler assembler;

    KakaoBookClient(
            final KakaoBookProperties kakaoBookProperties,
            final KakaoBookAssembler assembler
    ) {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final var clientProperties = kakaoBookProperties.getClient();
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(clientProperties.getConnectionTimeout())
                .build();

        baseUri = URI.create(kakaoBookProperties.getUrl());

        final var authenticationValue = AuthenticationValue.of(
                kakaoBookProperties.getAuthorizationScheme(),
                kakaoBookProperties.getApiKey()
        );
        authenticationHeader = AuthenticationHeader.of(
                kakaoBookProperties.getAuthorizationHeaderKey(),
                authenticationValue
        );

        this.assembler = assembler;

        client2 = new RestTemplateBuilder()
                .rootUri(kakaoBookProperties.getUrl())
                .defaultHeader(authenticationHeader.getKey(),
                        authenticationHeader.getKey())
                .setReadTimeout(clientProperties.getReadTimeout())
                .setConnectTimeout(clientProperties.getConnectionTimeout())
                .build();
    }

    @Cacheable(CacheKey.KakaoBookSearch)
    @Override
    public BookSearchResponse search(final BookSearchOption bookSearchOption) throws BookSearchFailException {
        final var uri = UriComponentsBuilder.fromUri(baseUri)
                .queryParam(
                        TARGET_KEY,
                        bookSearchOption.getTarget()
                )
                .queryParam(
                        SEARCH_KEY,
                        bookSearchOption.getValue()
                )
                .build()
                .toUri();

        final var request = HttpRequest.newBuilder(uri)
                .header(
                        authenticationHeader.getKey(),
                        authenticationHeader.getValue()
                )
                .GET()
                .build();

        final var response = exchange(request);

        return () -> response.getDocuments()
                .stream()
                .map(assembler::mapFrom)
                .collect(toUnmodifiableList());
    }

    private KakaoBookResponse exchange(final HttpRequest request) throws BookSearchFailException {
        final HttpResponse<String> response;
        try {
            response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
        } catch (final IOException | InterruptedException e) {
            log.error("Kakao book API call fail ", e);
            throw new BookSearchFailException(e);
        }

        final var httpStatus = HttpStatus.valueOf(response.statusCode());
        final var body = response.body();

        if (httpStatus.isError()) {
            log.error(
                    "Kakao book API fail [httpStatus={}, body={}]",
                    httpStatus,
                    body
            );
            throw new BookSearchFailException();
        }

        return parse(body);
    }

    private KakaoBookResponse parse(final String body) throws KakaoBookResponseParseException {
        try {
            return objectMapper.readValue(
                    body,
                    KakaoBookResponse.class
            );
        } catch (final JsonProcessingException e) {
            log.error("Kakao Book search response parse fail ", e);
            throw new KakaoBookResponseParseException(e);
        }
    }
}
