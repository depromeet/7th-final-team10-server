package com.depromeet.boiledegg.common.utils.random;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class RandomStringTest {

    @Test
    void getSecureString() {
        // given
        final var constraints = new HashSet<String>();

        for (var i = 0; i < 10; i++) {
            for (var length = 20; length <= 50; length++) {
                // when
                final var secureString = RandomString.getSecureString(length);

                // then
                assertThat(secureString.length()).isEqualTo(length);
                assertThat(constraints.contains(secureString)).isFalse();

                constraints.add(secureString);
            }
        }
    }

    @Test
    void getTimebaseUuid() {
        // given
        final var constraints = new HashSet<String>();

        for (var i = 0; i < 10; i++) {
            // when
            final var secureString = RandomString.getTimebaseUuid();

            // then
            assertThat(constraints.contains(secureString)).isFalse();

            constraints.add(secureString);
        }
    }
}