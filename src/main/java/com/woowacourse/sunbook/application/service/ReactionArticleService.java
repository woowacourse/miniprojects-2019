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
    private UserService userService;

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

        if (!reactionArticleRepository.existsByAuthorAndArticle(author, article)) {
            reactionArticleRepository.save(new ReactionArticle(author, article));
        }

        ReactionArticle reactionArticle = reactionArticleRepository
                .findByAuthorAndArticle(author, article);
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
        return reactionArticleRepository.findAllByArticle(article).stream()
                .filter(reactionArticle -> reactionArticle.getHasGood())
                .count()
                ;
    }

    private boolean isClickedGoodInArticleByLoginUser(User loginUser, Article article) {
        if (reactionArticleRepository.existsByAuthorAndArticle(loginUser, article)) {
            ReactionArticle reactionArticle = reactionArticleRepository
                    .findByAuthorAndArticle(loginUser, article);
            return reactionArticle.getHasGood();
        }
        return false;
    }
}
