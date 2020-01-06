package com.depromeet.boiledegg.common.utils.random;

import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public final class AlphaNumeric {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final String value = Alphabet.getUpperCase() + Alphabet.getLowerCase() + Digits.getDigits();

    public char getRandomChar() {
        return value.charAt(random.nextInt(length()));
    }

    public int length() {
        return value.length();
    }
}
