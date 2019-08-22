package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.reaction.ReactionArticle;
import com.woowacourse.sunbook.domain.reaction.ReactionArticleRepository;
import com.woowacourse.sunbook.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ReactionArticleServiceTest {
    private static final Long AUTHOR_ID = 1L;
    private static final Long ARTICLE_ID = 1L;

    @InjectMocks
    private ReactionArticleService reactionArticleService;

    @Mock
    private ReactionArticleRepository reactionArticleRepository;

    @Mock
    private ArticleService articleService;

    @Mock
    private UserService userService;

    @Mock
    private User author;

    @Mock
    private Article article;

    @Mock
    private ReactionArticle reactionArticle;

    @Test
    void 좋아요_처음_누르기_정상() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(articleService.findById(ARTICLE_ID)).willReturn(article);
        given(reactionArticleRepository.existsByAuthorAndArticle(author, article)).willReturn(false);
        given(reactionArticleRepository.findByAuthorAndArticle(author, article)).willReturn(reactionArticle);

        reactionArticleService.clickGood(ARTICLE_ID, AUTHOR_ID);

        verify(reactionArticleRepository).save(any(ReactionArticle.class));
    }

    @Test
    void 좋아요_취소_정상() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(articleService.findById(ARTICLE_ID)).willReturn(article);
        given(reactionArticleRepository.existsByAuthorAndArticle(author, article)).willReturn(true);
        given(reactionArticleRepository.findByAuthorAndArticle(author, article)).willReturn(reactionArticle);

        reactionArticleService.clickGood(ARTICLE_ID, AUTHOR_ID);

        verify(reactionArticleRepository, never()).save(any(ReactionArticle.class));
    }

    @Test
    void 좋아요_정상_조회() {
        given(articleService.findById(ARTICLE_ID)).willReturn(article);

        reactionArticleService.showCount(ARTICLE_ID);

        verify(reactionArticleRepository).findAllByArticle(article);
    }
}
