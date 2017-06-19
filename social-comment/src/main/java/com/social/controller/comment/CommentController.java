package com.social.controller.comment;

import com.social.domain.Comment;
import com.social.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by KimYJ on 2017-06-08.
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{serviceType}/{articleIdx}")
    public String showComments(@PathVariable(name = "serviceType") String serviceType,
                               @PathVariable(name = "articleIdx") String articleIdx,
                               @PageableDefault(sort = "parentIdx", direction = Sort.Direction.DESC) Pageable pageable, OAuth2Authentication auth, Model model) {

        pageable = new PageRequest(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Comment> commentPage = commentService.getComments(pageable);
        model.addAttribute("commentPage", commentPage);
        model.addAttribute("articleIdx", articleIdx);
        model.addAttribute("isAuthenticated", auth == null ? false : auth.isAuthenticated());
        return "/comment";
    }

    @ResponseBody
    @GetMapping("/json/{serviceType}/{articleIdx}")
    public Page<Comment> showJsonComments(@PathVariable(name = "serviceType") String serviceType,
                               @PathVariable(name = "articleIdx") String articleIdx,
                               @PageableDefault(sort = "parentIdx", direction = Sort.Direction.DESC) Pageable pageable) {

        pageable = new PageRequest(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Comment> commentPage = commentService.getComments(pageable);
        return commentPage;
    }

}
