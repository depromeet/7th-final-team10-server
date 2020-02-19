package com.depromeet.boiledegg.common.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Set;

import static java.util.Objects.isNull;

@UtilityClass
public final class Collections {

    public boolean isEmpty(final Collection<?> value) {
        return isNull(value) || value.isEmpty();
    }

    public static <T> Set<T> emptySet() {
        return java.util.Collections.emptySet();
    }
}
