package com.social.domain;


import com.social.domain.enums.Status;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by KimYJ on 2017-06-08.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_idx")
    private long commentIdx;

    @Column(name = "content")
    private String content;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "article_idx")
    private long articleIdx;

    @Column(name = "parent_idx")
    private long parentIdx;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "warning_cnt")
    private long warningCnt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="user_idx")
    private User user;


}
