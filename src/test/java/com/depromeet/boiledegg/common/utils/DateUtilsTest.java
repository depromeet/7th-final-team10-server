package com.depromeet.boiledegg.common.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    void parse() {
        assertThat(DateUtils.parse("2016-02-26T00:00:00.000+09:00")).isNotEmpty();
    }

    @Test
    void parse_fail() {
        assertThat(DateUtils.parse(null)).isEmpty();
        assertThat(DateUtils.parse("")).isEmpty();
        assertThat(DateUtils.parse("sdasda")).isEmpty();
    }
}