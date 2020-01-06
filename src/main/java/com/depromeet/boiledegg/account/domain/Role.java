package com.depromeet.boiledegg.account.domain;

public enum Role {

    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    public String getRole() {
        return PREFIX + name();
    }
}
