package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.reaction.ReactionDto;
import com.woowacourse.sunbook.application.service.ReactionArticleService;
import com.woowacourse.sunbook.presentation.support.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles/{articleId}/good")
public class ReactionArticleApiController {
    private final ReactionArticleService reactionArticleService;

    @Autowired
    public ReactionArticleApiController(ReactionArticleService reactionArticleService) {
        this.reactionArticleService = reactionArticleService;
    }

    @GetMapping
    public ResponseEntity<ReactionDto> show(@PathVariable Long articleId,
                                                LoginUser loginUser) {
        ReactionDto reactionDto = reactionArticleService
                .showCount(loginUser.getId(), articleId);

        return new ResponseEntity<>(reactionDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReactionDto> save(@PathVariable Long articleId,
                                                 LoginUser loginUser) {
        ReactionDto reactionDto = reactionArticleService.save(loginUser.getId(), articleId);

        return new ResponseEntity<>(reactionDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ReactionDto> remove(@PathVariable Long articleId,
                                                  LoginUser loginUser) {
        ReactionDto reactionDto = reactionArticleService.remove(loginUser.getId(), articleId);

        return new ResponseEntity<>(reactionDto, HttpStatus.OK);
    }
}
