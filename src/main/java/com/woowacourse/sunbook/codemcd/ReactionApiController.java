package com.woowacourse.sunbook.codemcd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/articles/{articleId}/good")
    public ResponseEntity<ReactionDto> showGood(@PathVariable Long articleId) {
        ReactionDto reactionDto = reactionArticleService.getCount(articleId);
        return new ResponseEntity<>(reactionDto, HttpStatus.OK);
    }
}
