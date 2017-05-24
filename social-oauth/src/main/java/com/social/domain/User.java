package com.social.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

/**
 * Created by KimYJ on 2017-05-23.
 */
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userIdx;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_principal")
    private String userPrincipal;

    @Column(name="social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name="user_key")
    private int userKey;

    @Column(name = "user_profile_url")
    private String userProfileUrl;

    @Column(name = "user_url")
    private String userUrl;

    public User() {}

    public User(String userName, String userPrincipal, SocialType socialType, String userProfileUrl, String userUrl) {
        this.userName = userName;
        this.userPrincipal = userPrincipal;
        this.socialType = socialType;
        this.userProfileUrl = userProfileUrl;
        this.userUrl = userUrl;
    }

    @Override
    public int hashCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(userName).append(userPrincipal).append(socialType);
        int a= builder.toString().hashCode();
        System.out.println(a);
        return builder.hashCode();
    }

}
