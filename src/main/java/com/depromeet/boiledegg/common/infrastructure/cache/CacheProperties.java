package com.depromeet.boiledegg.common.infrastructure.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@AllArgsConstructor
public enum CacheProperties {

    KAKAO_BOOK_SEARCH(
            CacheKey.KakaoBookSearch,
            Duration.ofDays(3)
    )
    ;

    @Getter
    private final String cacheName;

    @Getter
    private final Duration expiredAfterWrite;

    private final Integer initialCapacity;

    private final Integer maximumSize;

    CacheProperties(
            final String cacheName,
            final Duration expiredAfterWrite
    ) {
        this(
                cacheName,
                expiredAfterWrite,
                null,
                null
        );
    }

    public void consumeInitialCapacity(final Consumer<Integer> consumer) {
        if (isNull(initialCapacity)) {
            return;
        }

        consumer.accept(initialCapacity);
    }

    public void consumeMaximumSize(final Consumer<Integer> consumer) {
        if (isNull(maximumSize)) {
            return;
        }

        consumer.accept(maximumSize);
    }

    public static Stream<CacheProperties> stream() {
        return Arrays.stream(values());
    }
}
