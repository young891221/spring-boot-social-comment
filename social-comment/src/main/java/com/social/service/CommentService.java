package com.social.service;

import com.social.domain.Article;
import com.social.domain.Comment;
import com.social.repository.ArticleRepository;
import com.social.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Created by KimYJ on 2017-06-08.
 */
@Service
@Transactional
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @PostConstruct
    public void testInit() {
        createTestArticle();
    }

    public void createTestArticle() {
        articleRepository.save(Article.builder().title("test")
                .content("테스트 글입니다.")
                .serviceType("test")
                .build());
    }

    public void createComment() {

    }

    public List<Comment> getComments() {
        return null;
    }
}
