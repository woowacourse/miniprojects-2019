package com.woowacourse.sunbook.codemcd;

import com.woowacourse.sunbook.application.article.ArticleService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ReactionArticleServiceTest {

    @InjectMocks
    private ReactionArticleService reactionArticleService;

    @Mock
    private ReactionArticleRepository reactionArticleRepository;

    @Mock
    private ArticleService articleService;
}
