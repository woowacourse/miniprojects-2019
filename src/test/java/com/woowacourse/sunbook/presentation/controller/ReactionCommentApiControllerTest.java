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

    // TODO: 전체 테스트 실행하면 해당 제이슨 경로를 찾지 못한다는 에러 발생
    @Test
    void 초기_좋아요_개수_정상_조회() {
        // 좋아요 조회
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.GET, "/api/comments/2/good", Void.class, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                .jsonPath("$.hasGood").isEqualTo(false)
        ;
    }

    @Test
    void 좋아요_후_개수_정상_조회() {
        // 좋아요 누르기(TestTemplate 로 분리하기)
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/comments/5/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
        ;

        // 좋아요 조회
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

        respondApi(loginAndRequest(HttpMethod.POST, "/api/comments/4/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                .jsonPath("$.hasGood").isEqualTo(false)
        ;
    }
}
