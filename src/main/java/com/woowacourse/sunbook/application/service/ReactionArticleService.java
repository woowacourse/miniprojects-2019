package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.reaction.ReactionDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.reaction.ReactionArticle;
import com.woowacourse.sunbook.domain.reaction.ReactionArticleRepository;
import com.woowacourse.sunbook.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReactionArticleService {
    private ReactionArticleRepository reactionArticleRepository;
    private ArticleService articleService;
    private LoginService loginService;
    private ModelMapper modelMapper;

    @Autowired
    public ReactionArticleService(final ReactionArticleRepository reactionArticleRepository,
                                  final ArticleService articleService,
                                  final LoginService loginService,
                                  final ModelMapper modelMapper) {
        this.reactionArticleRepository = reactionArticleRepository;
        this.articleService = articleService;
        this.loginService = loginService;
        this.modelMapper = modelMapper;
    }

    // TODO: 로직을 빼는거, dto의 값에 의존하지 말자
    @Transactional
    public ReactionDto clickGood(final Long articleId, final ReactionDto requestReactionDto, final Long userId) {
        User author = loginService.findById(userId);
        Article article = articleService.findById(articleId);
        Long currentOfCount = requestReactionDto.getNumberOfGood();

        return new ReactionDto(a(author, article) + currentOfCount);
    }

    private Long a(User author, Article article) {
        if (reactionArticleRepository.existsByAuthorAndArticle(author, article)) {
            deleteGood(article.getAuthor(), article);

            return -1L;
        }

        ReactionArticle reactionArticle = new ReactionArticle(article.getAuthor(), article);
        reactionArticle.addGood();
        reactionArticleRepository.save(reactionArticle);

        return 1L;
    }


    private void deleteGood(final User author, final Article article) {
        reactionArticleRepository.deleteByAuthorAndArticle(author, article);
    }

    public ReactionDto showCount(final Long articleId) {
        Article article = articleService.findById(articleId);
        Long countOfGood = getCount(article);
        return new ReactionDto(countOfGood);
    }

    private Long getCount(final Article article) {
        return reactionArticleRepository.countByArticle(article);
    }
}
