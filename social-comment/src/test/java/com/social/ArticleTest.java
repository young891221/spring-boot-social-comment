package com.social;

import com.social.domain.Article;
import com.social.repository.ArticleRepository;
import com.social.service.CommentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by KimYJ on 2017-06-08.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SocialApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleTest {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleRepository articleRepository;

    @Before
    public void init() {
        commentService.createTestArticle();
    }

    @Test
    public void 테스트_글_생성_테스트() {
        Article article = articleRepository.findOne((long) 1);
        assertThat(article.getTitle(), is("test"));
        assertThat(article.getContent(), is("테스트 글입니다."));
        assertThat(article.getServiceType(), is("test"));
    }
}
