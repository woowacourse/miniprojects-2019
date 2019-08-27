package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.MockStorage;
import com.woowacourse.sunbook.domain.reaction.ReactionArticle;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ReactionArticleServiceTest extends MockStorage {
    private static final Long AUTHOR_ID = 1L;
    private static final Long ARTICLE_ID = 1L;

    @InjectMocks
    private ReactionArticleService injectReactionArticleService;

    @Test
    void 좋아요_처음_누르기_정상() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(articleService.findById(ARTICLE_ID)).willReturn(article);
        given(reactionArticleRepository.findByAuthorAndArticle(author, article))
                .willReturn(Optional.of(reactionArticle));

        injectReactionArticleService.clickGood(AUTHOR_ID, ARTICLE_ID);

        verify(reactionArticleRepository).save(any(ReactionArticle.class));
    }

    @Test
    void 좋아요_취소_정상() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(articleService.findById(ARTICLE_ID)).willReturn(article);
        given(reactionArticleRepository.findByAuthorAndArticle(author, article))
                .willReturn(Optional.of(reactionArticle));

        injectReactionArticleService.clickGood(AUTHOR_ID, ARTICLE_ID);

        verify(reactionArticleRepository, never()).save(any(ReactionArticle.class));
    }

    @Test
    void 좋아요가_눌린_게시글_정상_조회() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(articleService.findById(ARTICLE_ID)).willReturn(article);
        given(reactionArticleRepository.findByAuthorAndArticle(author, article))
                .willReturn(Optional.of(reactionArticle));

        injectReactionArticleService.showCount(AUTHOR_ID, ARTICLE_ID);

        verify(reactionArticleRepository).findAllByArticle(article);
    }

    @Test
    void 좋아요가_눌리지_않은_게시글_정상_조회() {
        given(userService.findById(AUTHOR_ID)).willReturn(author);
        given(articleService.findById(ARTICLE_ID)).willReturn(article);

        injectReactionArticleService.showCount(AUTHOR_ID, ARTICLE_ID);

        verify(reactionArticleRepository).findAllByArticle(article);
    }
}
