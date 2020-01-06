package com.depromeet.boiledegg.common.utils.random;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Alphabet {

    @Getter
    private final String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Getter
    private final String lowerCase = upperCase.toLowerCase();
}
