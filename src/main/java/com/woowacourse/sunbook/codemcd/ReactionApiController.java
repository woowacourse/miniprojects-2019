package com.woowacourse.sunbook.codemcd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class ReactionApiController {

    private ReactionArticleService reactionArticleService;

    @Autowired
    public ReactionApiController(ReactionArticleService reactionArticleService) {
        this.reactionArticleService = reactionArticleService;
    }

    @PostMapping("/articles/{articleId}/good")
    @Transactional
    public ResponseEntity<ReactionDto> saveGood(@PathVariable Long articleId) {
        ReactionDto reactionDto = reactionArticleService.saveGood(articleId);
        return new ResponseEntity<>(reactionDto, HttpStatus.OK);
    }
}
