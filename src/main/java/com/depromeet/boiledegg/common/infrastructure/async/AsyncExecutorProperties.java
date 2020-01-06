package com.depromeet.boiledegg.common.infrastructure.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "boiled-egg.async-executor")
final class AsyncExecutorProperties {

    private int corePoolSize;

    private int maxPoolSize;

    private int queueCapacity;
}
