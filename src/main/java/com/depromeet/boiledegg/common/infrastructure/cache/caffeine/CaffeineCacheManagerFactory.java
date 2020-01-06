package com.depromeet.boiledegg.common.infrastructure.cache.caffeine;

import com.depromeet.boiledegg.common.infrastructure.cache.CacheManagerFactory;
import com.depromeet.boiledegg.common.infrastructure.cache.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toSet;

@Component
@AllArgsConstructor
final class CaffeineCacheManagerFactory implements CacheManagerFactory {

    @Override
    public CacheManager create() {
        final var manager = new SimpleCacheManager();

        final var caches = CacheProperties.stream()
                .map(this::createCache)
                .collect(toSet());
        manager.setCaches(caches);

        return manager;
    }

    public Cache createCache(final CacheProperties properties) {
        var builder = Caffeine.newBuilder()
                .expireAfterWrite(properties.getExpiredAfterWrite())
                .softValues()
                .recordStats();

        properties.consumeInitialCapacity(builder::initialCapacity);
        properties.consumeMaximumSize(builder::maximumSize);

        return new CaffeineCache(
                properties.getCacheName(),
                builder.build()
        );
    }
}