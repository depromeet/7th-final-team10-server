package com.depromeet.boiledegg.common.infrastructure.async;

import org.springframework.lang.NonNull;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
final class AsyncExecutor implements Executor {

    private static final String THREAD_NAME_PREFIX = "Boiled-Egg-Async-ThreadPool-";

    private final Executor executor;

    public AsyncExecutor(final AsyncExecutorProperties properties) {
        final var threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(properties.getCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(properties.getMaxPoolSize());
        threadPoolTaskExecutor.setQueueCapacity(properties.getQueueCapacity());
        threadPoolTaskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        threadPoolTaskExecutor.initialize();

        executor = threadPoolTaskExecutor;
    }

    @Override
    public void execute(@NonNull final Runnable command) {
        executor.execute(command);
    }
}
