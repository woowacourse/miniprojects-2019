package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class LoginApiControllerTest extends TestTemplate {
    private static final String USER_EMAIL = "ddu0422@naver.com";
    private static final String USER_NAME = "mir";
    private static final String USER_PASSWORD = "asdf1234!A";
    private static final String WRONG_USER_PASSWORD = "ASDF1324!a";

    private UserEmail userEmail = new UserEmail(USER_EMAIL);
    private UserName userName = new UserName(USER_NAME);
    private UserPassword userPassword = new UserPassword(USER_PASSWORD);
    private UserPassword wrongUserPassword = new UserPassword(WRONG_USER_PASSWORD);
    private UserRequestDto userLoginRequestDto = new UserRequestDto(userEmail, userName, userPassword);
    private UserRequestDto userWrongRequestDto = new UserRequestDto(userEmail, userName, wrongUserPassword);

    @Test
    void 로그인_성공() {
        respondApi(request(HttpMethod.POST, "/api/signin", userLoginRequestDto, HttpStatus.OK))
                .jsonPath("$..email").isEqualTo(USER_EMAIL)
                .jsonPath("$..name").isEqualTo(USER_NAME)
                ;
    }

    @Test
    void 로그인_실패() {
        respondApi(request(HttpMethod.POST, "/api/signin", userWrongRequestDto, HttpStatus.OK))
                .jsonPath("$.errorMessage").isEqualTo("로그인 실패")
                ;
    }
}
