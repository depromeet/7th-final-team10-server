package com.depromeet.boiledegg.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Instant;

import java.util.Date;
import java.util.Optional;

@Slf4j
@UtilityClass
public class DateUtils {

    public Optional<Date> parse(final String datetime) {
        if (Strings.isEmpty(datetime)) {
            return Optional.empty();
        }

        try {
            return Optional.ofNullable(Instant.parse(datetime).toDate());
        } catch (final IllegalArgumentException e) {
            log.error("Date parse fail [source={}]", datetime, e);
            return Optional.empty();
        }
    }
}
