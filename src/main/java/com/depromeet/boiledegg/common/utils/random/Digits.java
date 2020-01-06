package com.depromeet.boiledegg.common.utils.random;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

@UtilityClass
public final class Digits {

    @Getter
    private final String digits = IntStream.rangeClosed(Digit.MIN, Digit.MAX)
            .mapToObj(Digit::valueOf)
            .map(Digit::toString)
            .collect(joining());
}
