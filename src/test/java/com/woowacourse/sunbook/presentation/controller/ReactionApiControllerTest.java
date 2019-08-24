package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.reaction.ReactionDto;
import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class ReactionApiControllerTest extends TestTemplate {
    ReactionDto reactionDto = new ReactionDto(0L, false);

    @Test
    void 좋아요_정상_동작() {
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles/1/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
                ;
    }

    @Test
    void 초기_좋아요_개수_정상_조회() {
        // 좋아요 조회
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.GET, "/api/articles/2/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                .jsonPath("$.hasGood").isEqualTo(false)
                ;
    }

    @Test
    void 좋아요_후_개수_정상_조회() {
        // 좋아요 누르기(TestTemplate 로 분리하기)
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles/5/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
                ;

        // 좋아요 조회
        respondApi(loginAndRequest(HttpMethod.GET, "/api/articles/5/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
                ;
    }

    @Test
    void 좋아요_취소_정상() {
        String sessionId = loginSessionId(userRequestDto);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles/4/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                .jsonPath("$.hasGood").isEqualTo(true)
                ;

        respondApi(loginAndRequest(HttpMethod.POST, "/api/articles/4/good", reactionDto, HttpStatus.OK, sessionId))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                .jsonPath("$.hasGood").isEqualTo(false)
                ;
    }
}
