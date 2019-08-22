package com.woowacourse.sunbook.presentation.controller;

import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserUpdateRequestDto;
import com.woowacourse.sunbook.domain.user.UserChangePassword;
import com.woowacourse.sunbook.domain.user.UserEmail;
import com.woowacourse.sunbook.domain.user.UserName;
import com.woowacourse.sunbook.domain.user.UserPassword;
import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.hasItem;

class UserApiControllerTest extends TestTemplate {

    private static final Long id = 1L;
    private static final String userEmail = "ddu0422@naver.com";
    private static final String userName = "mir";
    private static final String userPassword = "asdf1234!A";

    @Test
    void 현재_로그인한_사용자_조회() {
        respondApi(loginAndRequest(HttpMethod.GET, "/api/users", Void.class, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..id").value(hasItem(id.intValue()))
                .jsonPath("$..userEmail.email").value(hasItem(userEmail))
                .jsonPath("$..userName.name").value(hasItem(userName))
        ;
    }

    @Test
    void 현재_로그인한_사용자_수정() {
        final String changeEmail = "change@naver.com";
        final String changeName = "change";
        final String changePassword = "12qw!@QW";

        UserRequestDto userRequestDto = new UserRequestDto(
                new UserEmail("eara12sa@naver.com"),
                new UserName("abc"),
                new UserPassword("asdf1234!A")
        );

        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(
                new UserEmail(changeEmail),
                new UserName(changeName),
                new UserPassword(userPassword),
                new UserChangePassword(changePassword));

        respondApi(loginAndRequest(HttpMethod.PUT, "/api/users", userUpdateRequestDto, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..id").value(hasItem(2))
                .jsonPath("$..userEmail.email").value(hasItem(changeEmail))
                .jsonPath("$..userName.name").value(hasItem(changeName))
        ;
    }
}