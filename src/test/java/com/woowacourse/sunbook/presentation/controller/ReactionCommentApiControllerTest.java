package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.reaction.ReactionDto;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class ReactionCommentApiControllerTest extends TestTemplate {
    ReactionDto reactionDto = new ReactionDto(0L, false);

    @Test
    void 좋아요_정상_동작() {
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/comments/1/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
        ;
    }

    @Test
    void 초기_좋아요_개수_정상_조회() {
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.GET, "/api/comments/6/good", Void.class, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                .jsonPath("$.hasGood").isEqualTo(false)
        ;
    }

    @Test
    void 좋아요_후_개수_정상_조회() {
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/comments/5/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
        ;

        respondApi(loginAndRequest(HttpMethod.GET, "/api/comments/5/good", Void.class, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
        ;
    }

    @Test
    void 좋아요_취소_정상() {
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/comments/4/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
        ;

        respondApi(loginAndRequest(HttpMethod.DELETE, "/api/comments/4/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                .jsonPath("$.hasGood").isEqualTo(false)
        ;
    }
}
