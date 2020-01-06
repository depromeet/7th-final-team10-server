package com.depromeet.boiledegg.common.infrastructure.async;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AsyncExecutorPropertiesTest {

    @Autowired
    private AsyncExecutorProperties asyncExecutorProperties;

    @Test
    void load() {
        assertThat(asyncExecutorProperties).isNotNull();
    }
}