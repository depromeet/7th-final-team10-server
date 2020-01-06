package com.depromeet.boiledegg.common.infrastructure.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
class CacheConfig {

    private final CacheManagerFactory cacheManagerFactory;

    @Bean
    public CacheManager cacheManager() {
        return cacheManagerFactory.create();
    }
}
