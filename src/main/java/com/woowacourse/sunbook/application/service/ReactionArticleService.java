package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.reaction.ReactionDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.reaction.ReactionArticle;
import com.woowacourse.sunbook.domain.reaction.ReactionArticleRepository;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReactionArticleService {
    private ReactionArticleRepository reactionArticleRepository;
    private ArticleService articleService;
    private LoginService loginService;

    @Autowired
    public ReactionArticleService(final ReactionArticleRepository reactionArticleRepository,
                                  final ArticleService articleService,
                                  final LoginService loginService) {
        this.reactionArticleRepository = reactionArticleRepository;
        this.articleService = articleService;
        this.loginService = loginService;
    }

    @Transactional
    public ReactionDto clickGood(final Long articleId, final Long userId) {
        User author = loginService.findById(userId);
        Article article = articleService.findById(articleId);

        if (!reactionArticleRepository.existsByAuthorAndArticle(author, article)) {
            reactionArticleRepository.save(new ReactionArticle(author, article));
        }

        ReactionArticle reactionArticle = reactionArticleRepository
                .findByAuthorAndArticle(author, article);
        reactionArticle.toggleGood();

        return new ReactionDto(getCount(article));
    }

    public ReactionDto showCount(final Long articleId) {
        Article article = articleService.findById(articleId);
        Long countOfGood = getCount(article);
        return new ReactionDto(countOfGood);
    }

    private Long getCount(final Article article) {
        return reactionArticleRepository.findAllByArticle(article).stream()
                .filter(reactionArticle -> reactionArticle.getHasGood())
                .count()
                ;
    }
}
