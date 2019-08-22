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
    private static final Long ID = 1L;
    private static final String USER_EMAIL = "ddu0422@naver.com";
    private static final String USER_NAME = "mir";
    private static final String USER_PASSWORD = "asdf1234!A";
    private static final String NEW_USER_EMAIL = "ddu0422@gmail.com";

    private UserEmail userEmail = new UserEmail(USER_EMAIL);
    private UserEmail newUserEmail = new UserEmail(NEW_USER_EMAIL);
    private UserName userName = new UserName(USER_NAME);
    private UserPassword userPassword = new UserPassword(USER_PASSWORD);

    private UserRequestDto userSignInRequestDto = new UserRequestDto(newUserEmail, userName, userPassword);
    private UserRequestDto userLoginRequestDto = new UserRequestDto(userEmail, userName, userPassword);

    @Test
    void 현재_로그인한_사용자_조회() {
        respondApi(loginAndRequest(HttpMethod.GET, "/api/users", Void.class, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..id").value(hasItem(ID.intValue()))
                .jsonPath("$..userEmail.email").value(hasItem(USER_EMAIL))
                .jsonPath("$..userName.name").value(hasItem(USER_NAME))
                ;
    }

    @Test
    void 회원가입_성공() {
        respondApi(request(HttpMethod.POST, "/api/users", userSignInRequestDto, HttpStatus.OK))
                .jsonPath("$..email").isEqualTo(NEW_USER_EMAIL)
                .jsonPath("$..name").isEqualTo(USER_NAME)
                ;
    }

    @Test
    void 중복된_이메일로_인한_회원가입_실패() {
        respondApi(request(HttpMethod.POST, "/api/users", userLoginRequestDto, HttpStatus.OK))
                .jsonPath("$.errorMessage").isEqualTo("중복된 이메일입니다.")
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
                new UserPassword(USER_PASSWORD),
                new UserChangePassword(changePassword));

        respondApi(loginAndRequest(HttpMethod.PUT, "/api/users", userUpdateRequestDto, HttpStatus.OK, loginSessionId(userRequestDto)))
                .jsonPath("$..id").value(hasItem(2))
                .jsonPath("$..userEmail.email").value(hasItem(changeEmail))
                .jsonPath("$..userName.name").value(hasItem(changeName))
                ;
    }
}