package com.social.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Created by KimYJ on 2017-05-18.
 */
public enum SocialType {
    FACEBOOK("facebook"),
    TWITTER("twitter"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String ROLE_PREFIX = "ROLE_";
    private String name;

    SocialType(String type) {
        this.name = type;
    }

    public String getRoleType() { return ROLE_PREFIX + name.toUpperCase(); }

    public String getNameType(Collection<? extends GrantedAuthority> roles) {
        return StringUtils.delete(String.valueOf(roles.toArray()[0]), ROLE_PREFIX);
    }

    public String getValue() { return name; }
}
