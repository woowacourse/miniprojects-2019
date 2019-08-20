package com.woowacourse.sunbook.codemcd;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.application.article.dto.ArticleResponseDto;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReactionApiController {

    @Autowired
    private ReactionArticleRepository reactionArticleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/articles/{articleId}/good")
    @Transactional
    public ResponseEntity<ReactionDto> saveGood(@PathVariable Long articleId) {
        List<ArticleResponseDto> articles = articleService.findAll();
//        Article article = modelMapper.map(articleService.findById(articleId), Article.class);
        Article article = articleService.findById(articleId);
        // 해당 게시글 작성자와 현제 세션 유저가 같은지 확인(checkSameUser)
        ReactionArticle reactionArticle = new ReactionArticle(article.getAuthor(), article);
        reactionArticle.addGood();
        reactionArticleRepository.save(reactionArticle);
        ReactionDto reactionDto = new ReactionDto(true);
        return new ResponseEntity<>(reactionDto, HttpStatus.OK);
    }

    private void checkSameUser(Article article, User author) {
        // article 에 있는 isSame() 메소드 호출해서 확인하기
    }
}
