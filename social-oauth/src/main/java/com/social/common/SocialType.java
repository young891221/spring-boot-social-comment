package com.social.common;

/**
 * Created by KimYJ on 2017-05-18.
 */
public enum SocialType {
    FACEBOOK("facebook"),
    TWITTER("twitter");

    private String role;

    SocialType(String type) {
        this.role = type;
    }

    public String getType() {
        return "ROLE_" + role.toUpperCase();
    }

    public String getValue() { return role; }
}
