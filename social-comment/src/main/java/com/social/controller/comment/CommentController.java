package com.social.controller.comment;

import com.social.domain.Comment;
import com.social.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
                               @PageableDefault(sort = "parentIdx", direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        pageable = new PageRequest(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Comment> commentPage = commentService.getComments(pageable);
        model.addAttribute("commentPage", commentPage);
        model.addAttribute("articleIdx", articleIdx);
        return "/comment";
    }

}
