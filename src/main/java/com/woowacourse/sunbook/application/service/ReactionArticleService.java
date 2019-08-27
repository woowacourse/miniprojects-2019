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
    private final ReactionArticleRepository reactionArticleRepository;
    private final ArticleService articleService;
    private final UserService userService;

    @Autowired
    public ReactionArticleService(final ReactionArticleRepository reactionArticleRepository,
                                  final ArticleService articleService,
                                  final UserService userService) {
        this.reactionArticleRepository = reactionArticleRepository;
        this.articleService = articleService;
        this.userService = userService;
    }

    @Transactional
    public ReactionDto clickGood(final Long userId, final Long articleId) {
        User author = userService.findById(userId);
        Article article = articleService.findById(articleId);

        ReactionArticle reactionArticle = reactionArticleRepository
                .findByAuthorAndArticle(author, article)
                .orElseGet(() ->
                        reactionArticleRepository.save(new ReactionArticle(author, article)));
        reactionArticle.toggleGood();

        return new ReactionDto(getCount(article), reactionArticle.getHasGood());
    }

    public ReactionDto showCount(final Long userId, final Long articleId) {
        User author = userService.findById(userId);
        Article article = articleService.findById(articleId);

        return new ReactionDto(getCount(article),
                isClickedGoodInArticleByLoginUser(author, article));
    }

    private Long getCount(final Article article) {
        return reactionArticleRepository.findAllByArticle(article)
                .stream()
                .filter(ReactionArticle::getHasGood)
                .count()
                ;
    }

    private boolean isClickedGoodInArticleByLoginUser(final User loginUser, final Article article) {
        return reactionArticleRepository
                .findByAuthorAndArticle(loginUser, article)
                .map(ReactionArticle::getHasGood)
                .orElse(false)
                ;
    }
}
