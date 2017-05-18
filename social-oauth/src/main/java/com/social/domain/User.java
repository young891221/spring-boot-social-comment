package com.social.domain;


import com.social.common.SocialType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by jeonghoon on 2016-12-14.
 */

@Entity
@Table(name = "user")
public class User {

    public User(String userName, String userPrincipal, Enum<SocialType> socialType, String userProfileUrl, String userUrl) {
        this.userName = userName;
        this.userPrincipal = userPrincipal;
        this.socialType = socialType.toString();
        this.userProfileUrl = userProfileUrl;
        this.userUrl = userUrl;

    }

    public User(){}

    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userIdx;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_principal")
    private String userPrincipal;

    @Column(name="social_type")
    private String socialType;

    @Column(name="user_key")
    private int userKey;

    @Column(name = "user_profile_url")
    private String userProfileUrl;

    @Column(name = "user_url")
    private String userUrl;

    @Override
    public int hashCode() {
        StringBuilder builder = new StringBuilder();
        builder.append(userName).append(userPrincipal).append(socialType);
        int a= builder.toString().hashCode();
        System.out.println(a);
        return builder.hashCode();
    }

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
    }

    public long getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(long userIdx) {
        this.userIdx = userIdx;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(String userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getUserProfileUrl() {
        return userProfileUrl;
    }

    public void setUserProfileUrl(String userProfileUrl) {
        this.userProfileUrl = userProfileUrl;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

}
