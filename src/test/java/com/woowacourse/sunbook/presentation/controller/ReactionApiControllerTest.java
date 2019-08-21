package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.reaction.ReactionDto;
import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class ReactionApiControllerTest extends TestTemplate {
    private static final String USER_EMAIL = "ddu0422@naver.com";
    private static final String USER_NAME = "mir";
    private static final String USER_PASSWORD = "asdf1234!A";

    private UserEmail userEmail = new UserEmail(USER_EMAIL);
    private UserName userName = new UserName(USER_NAME);
    private UserPassword userPassword = new UserPassword(USER_PASSWORD);

    private UserRequestDto userLoginRequestDto = new UserRequestDto(userEmail, userName, userPassword);
    ReactionDto reactionDto = new ReactionDto(0L);


    @BeforeEach
    void setUp() {
        // 로그인
        // 유저 정보는 data.sql로 미리 넣어둠
        respondApi(request(HttpMethod.POST, "/api/signin", userLoginRequestDto, HttpStatus.OK))
                .jsonPath("$..email").isEqualTo(USER_EMAIL)
                .jsonPath("$..name").isEqualTo(USER_NAME)
                ;

        // 게시글은 data.sql로 미리 넣어둠
    }

    @Test
    void 좋아요_정상_동작() {
        respondApi(request(HttpMethod.POST, "/api/articles/1/good",
                reactionDto, HttpStatus.OK))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                ;
    }

    @Test
    void 초기_좋아요_개수_정상_조회() {
        // 좋아요 조회
        respondApi(request(HttpMethod.GET, "/api/articles/2/good",
                reactionDto, HttpStatus.OK))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                ;
    }

    @Test
    void 좋아요_후_개수_정상_조회() {
        // 좋아요 누르기(TestTemplate로 분리하기)
        respondApi(request(HttpMethod.POST, "/api/articles/3/good",
                reactionDto, HttpStatus.OK))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                ;

        // 좋아요 조회
        respondApi(request(HttpMethod.GET, "/api/articles/3/good",
                reactionDto, HttpStatus.OK))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                ;
    }

    @Test
    void 좋아요_취소_정상() {
        respondApi(request(HttpMethod.POST, "/api/articles/4/good",
                reactionDto, HttpStatus.OK))
                .jsonPath("$.numberOfGood").isEqualTo(1L)
                ;

        ReactionDto savedGood = new ReactionDto(1L);

        respondApi(request(HttpMethod.POST, "/api/articles/4/good",
                savedGood, HttpStatus.OK))
                .jsonPath("$.numberOfGood").isEqualTo(0L)
                ;
    }
}
