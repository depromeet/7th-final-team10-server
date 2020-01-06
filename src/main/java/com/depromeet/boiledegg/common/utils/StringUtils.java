package com.depromeet.boiledegg.common.utils;

import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;

@UtilityClass
public final class StringUtils {

    public boolean isBlank(final String value) {
        return isNull(value) || value.isBlank();
    }

    public boolean isEmpty(final String value) {
        return isNull(value) || value.isEmpty();
    }
}
