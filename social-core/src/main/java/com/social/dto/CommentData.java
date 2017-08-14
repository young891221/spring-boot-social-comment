package com.social.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * Created by KimYJ on 2017-06-12.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
public class CommentData {
    @JsonProperty("content")
    private String content;
    @JsonProperty("articleIdx")
    private Long articleIdx;
    @JsonProperty("parentIdx")
    private Long parentIdx;
}
