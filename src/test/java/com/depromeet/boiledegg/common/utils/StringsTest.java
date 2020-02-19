package com.depromeet.boiledegg.common.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringsTest {

    @NullSource
    @ParameterizedTest
    void isBlank_null(final String value) {
        assertThat(Strings.isBlank(value)).isTrue();
    }

    @ValueSource(strings = {"", " ", "        "})
    @ParameterizedTest
    void isBlank_true(final String value) {
        assertThat(Strings.isBlank(value)).isTrue();
    }

    @ValueSource(strings = {".", " ,", "  ,      "})
    @ParameterizedTest
    void isBlank_false(final String value) {
        assertThat(Strings.isBlank(value)).isFalse();
    }

    @NullSource
    @ParameterizedTest
    void isEmpty_null(final String value) {
        assertThat(Strings.isEmpty(value)).isTrue();
    }

    @ValueSource(strings = {""})
    @ParameterizedTest
    void isEmpty_true(final String value) {
        assertThat(Strings.isEmpty(value)).isTrue();
    }

    @ValueSource(strings = {" ", ".", " ,", "  ,      "})
    @ParameterizedTest
    void isEmpty_false(final String value) {
        assertThat(Strings.isEmpty(value)).isFalse();
    }
}