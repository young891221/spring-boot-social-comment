package com.social.controller.comment;

import com.social.annotation.SocialUser;
import com.social.domain.Comment;
import com.social.dto.CommentData;
import com.social.domain.User;
import com.social.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by KimYJ on 2017-06-12.
 */
@RestController
@RequestMapping(value="/api/comment")
public class CommentApiController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post")
    public Comment postComment(@SocialUser User user, @RequestBody CommentData commentData, HttpServletRequest request) {
        return commentService.createComment(Comment.builder()
                .clientIp(request.getRemoteAddr())
                .content(commentData.getContent())
                .articleIdx(commentData.getArticleIdx())
                .parentIdx(commentData.getParentIdx())
                .userName(user.getUserName())
                .build());
    }

}
