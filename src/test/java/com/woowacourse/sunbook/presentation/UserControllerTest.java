package com.woowacourse.sunbook.presentation;

import com.woowacourse.sunbook.presentation.template.TestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class UserControllerTest extends TestTemplate {

    @Test
    void 로그인_회원가입_페이지_이동() {
        request(HttpMethod.GET, "/", Void.class, HttpStatus.OK);
    }

    @Test
    void 메인_페이지_이동() {
        request(HttpMethod.GET, "/sunbook", Void.class, HttpStatus.OK);
    }

    @Test
    void 로그아웃_성공() {
        request(HttpMethod.POST, "/signout", Void.class, HttpStatus.FOUND);
    }
}
