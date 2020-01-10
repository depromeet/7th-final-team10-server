package com.depromeet.boiledegg.user.domain;

public enum Role {

    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    public String getRole() {
        return PREFIX + name();
    }
}
