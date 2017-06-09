package com.social.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by KimYJ on 2017-05-23.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user",
        indexes = {@Index(name = "ix_user_principal",  columnList="user_principal", unique = true),
                    @Index(name = "ix_user_email",  columnList="user_email")})
public class User implements Serializable{
    private static final long serialVersionUID = -1790606364187219092L;

    @Id
    @Column(name = "user_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userIdx;

    @Column(name = "user_principal")
    private String userPrincipal;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_Image")
    private String userImage;

    @Column(name = "social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Builder
    public User(String userPrincipal, String userName, String userEmail, String userImage, SocialType socialType) {
        this.userPrincipal = userPrincipal;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImage = userImage;
        this.socialType = socialType;
        this.createDate = LocalDateTime.now();
    }

}
