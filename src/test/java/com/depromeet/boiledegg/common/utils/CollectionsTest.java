package com.depromeet.boiledegg.common.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionsTest {

    @Test
    void test() {
        assertThat("123".chars().anyMatch(value -> !Character.isDigit(value))).isFalse();
        assertThat("12a3".chars().anyMatch(value -> !Character.isDigit(value))).isTrue();
    }

    @Test
    void isEmpty_null() {
        assertThat(Collections.isEmpty(null)).isTrue();
    }

    @Test
    void isEmpty_true() {
        assertThat(Collections.isEmpty(List.of())).isTrue();
        assertThat(Collections.isEmpty(new ArrayList<>())).isTrue();
        assertThat(Collections.isEmpty(new HashSet<>())).isTrue();
    }

    @Test
    void isEmpty_false() {
        assertThat(Collections.isEmpty(List.of("a"))).isFalse();
        assertThat(Collections.isEmpty(Set.of("a"))).isFalse();
    }
}