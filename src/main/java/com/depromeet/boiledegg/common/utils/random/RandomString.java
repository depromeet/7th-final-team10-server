package com.depromeet.boiledegg.common.utils.random;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public final class RandomString {

    private final TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(
            EthernetAddress.constructMulticastAddress(ThreadLocalRandom.current()));

    public String getSecureString(final int length) {
        if (length < 1) {
            throw new IllegalArgumentException();
        }

        return generateSecureString(length);
    }

    public String getTimebaseUuid() {
        return timeBasedGenerator.generate()
                .toString()
                .replaceAll("-", "");
    }

    private String generateSecureString(final int length) {
        final var buffer = new char[length];
        for (var index = 0; index < buffer.length; index++) {
            buffer[index] = AlphaNumeric.getRandomChar();
        }

        return new String(buffer);
    }
}
