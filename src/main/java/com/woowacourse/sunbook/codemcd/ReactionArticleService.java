package com.woowacourse.sunbook.codemcd;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReactionArticleService {

    private ReactionArticleRepository reactionArticleRepository;
    private ArticleService articleService;

    @Autowired
    public ReactionArticleService(ReactionArticleRepository reactionArticleRepository,
                                  ArticleService articleService) {
        this.reactionArticleRepository = reactionArticleRepository;
        this.articleService = articleService;
    }

    @Transactional
    public ReactionDto clickGood(Long articleId, ReactionDto requestReactionDto) {
        //Article article = modelMapper.map(articleService.findById(articleId), Article.class);
        Article article = articleService.findById(articleId);
        Long currentOfCount = requestReactionDto.getNumberOfGood();
        // 해당 게시글 작성자와 현제 세션 유저가 같은지 확인(checkSameUser)
        // 이미 좋아요가 있으면 해당 객체 삭제
        if (reactionArticleRepository.existsByArticle(article)) {
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

    private void checkSameUser(Article article, User author) {
        // article 에 있는 isSame() 메소드 호출해서 확인하기
    }

    public ReactionDto showCount(Long articleId) {
        Article article = articleService.findById(articleId);
        Long countOfGood = getCount(article);
        return new ReactionDto(countOfGood);
    }

    private Long getCount(Article article) {
        return reactionArticleRepository.countByArticle(article);
    }
}
