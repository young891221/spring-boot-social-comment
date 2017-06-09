package com.social.domain;

/**
 * Created by KimYJ on 2017-06-08.
 */
public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive"),
    DELETE("delete");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() { return value; }
}
