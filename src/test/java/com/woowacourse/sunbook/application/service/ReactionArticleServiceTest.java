package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.domain.reaction.ReactionArticleRepository;
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
