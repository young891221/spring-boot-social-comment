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

    @Column(name="user_principal")
    private String userPrincipal;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_email")
    private int userEmail;

    @Column(name = "user_Image")
    private String userImage;

    @Column(name="social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    public User() {}

    @Override
    public int hashCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(userName).append(userPrincipal).append(socialType);
        return builder.hashCode();
    }

}
