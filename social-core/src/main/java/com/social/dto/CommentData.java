package com.social.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
    @JsonProperty("isShare")
    private boolean isShare;
}
