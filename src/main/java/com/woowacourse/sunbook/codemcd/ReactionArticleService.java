package com.woowacourse.sunbook.codemcd;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ReactionDto saveGood(Long articleId) {
        //        Article article = modelMapper.map(articleService.findById(articleId), Article.class);
        Article article = articleService.findById(articleId);
        // 해당 게시글 작성자와 현제 세션 유저가 같은지 확인(checkSameUser)
        ReactionArticle reactionArticle = new ReactionArticle(article.getAuthor(), article);
        reactionArticle.addGood();
        reactionArticleRepository.save(reactionArticle);
        ReactionDto reactionDto = new ReactionDto(true);
        return reactionDto;
    }

    private void checkSameUser(Article article, User author) {
        // article 에 있는 isSame() 메소드 호출해서 확인하기
    }
}
