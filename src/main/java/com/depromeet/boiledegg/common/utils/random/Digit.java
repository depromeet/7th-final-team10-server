package com.depromeet.boiledegg.common.utils.random;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class Digit {

    static final int MIN = 0;
    static final int MAX = 9;

    private final int value;

    private Digit(final int value) {
        validate(value);

        this.value = value;
    }

    private void validate(final int value) {
        if (MIN > value || value > MAX) {
            throw new IllegalArgumentException();
        }
    }

    public static Digit valueOf(final int value) {
        return new Digit(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
