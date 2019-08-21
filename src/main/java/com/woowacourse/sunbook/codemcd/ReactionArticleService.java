package com.woowacourse.sunbook.codemcd;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.application.user.dto.UserResponseDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReactionArticleService {

    private ReactionArticleRepository reactionArticleRepository;
    private ArticleService articleService;
    private ModelMapper modelMapper;

    @Autowired
    public ReactionArticleService(ReactionArticleRepository reactionArticleRepository,
                                  ArticleService articleService,
                                  ModelMapper modelMapper) {
        this.reactionArticleRepository = reactionArticleRepository;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public ReactionDto clickGood(Long articleId, ReactionDto requestReactionDto, UserResponseDto loginUser) {
        //Article article = modelMapper.map(articleService.findById(articleId), Article.class);
        Article article = articleService.findById(articleId);
        Long currentOfCount = requestReactionDto.getNumberOfGood();
//        User author = modelMapper.map(loginUser, User.class);
        // 이미 좋아요가 있으면 해당 객체 삭제
//        if (reactionArticleRepository.existsByAuthorAndArticle(author, article)) {
//            deleteGood(author, article);
//            return new ReactionDto(currentOfCount - 1);
//        }
        if (reactionArticleRepository.existsByAuthorIdAndArticleId(1L, articleId)) {
            deleteGood(article.getAuthor(), article);
            return new ReactionDto(currentOfCount - 1);
        }
        ReactionArticle reactionArticle = new ReactionArticle(article.getAuthor(), article);
        reactionArticle.addGood();
        reactionArticleRepository.save(reactionArticle);
        return new ReactionDto(currentOfCount + 1);
    }

    private void deleteGood(User author, Article article) {
        reactionArticleRepository.deleteByAuthorAndArticle(author, article);
    }

    public ReactionDto showCount(Long articleId) {
        Article article = articleService.findById(articleId);
        Long countOfGood = getCount(articleId);
        return new ReactionDto(countOfGood);
    }



    private Long getCount(Article article) {
        return reactionArticleRepository.countByArticle(article);
    }

    private Long getCount(Long articleId) {
        return reactionArticleRepository.countByArticleId(articleId);
    }
}
