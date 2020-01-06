package com.depromeet.boiledegg.common.infrastructure.cache;

import org.springframework.cache.CacheManager;

@FunctionalInterface
public interface CacheManagerFactory {

    CacheManager create();
}
