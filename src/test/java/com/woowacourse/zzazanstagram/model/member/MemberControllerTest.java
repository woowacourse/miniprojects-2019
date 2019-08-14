package com.woowacourse.zzazanstagram.model.member;

import com.woowacourse.zzazanstagram.model.RequestTemplate;
import com.woowacourse.zzazanstagram.model.support.WebTestHelper;
import org.junit.jupiter.api.Test;

class MemberControllerTest extends RequestTemplate {

    @Test
    void 회원가입_페이지_이동() {
        getRequest("/signup")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void 회원가입_성공() {
        postRequest("/members")
                .body(WebTestHelper.userSignUpForm("test2@gmail.com",
                        "myName",
                        "https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-600w-1048185397.jpg",
                        "myNick",
                        "Password!1"))
                .exchange()
                .expectHeader()
                .valueMatches("location", ".*/login")
                .expectStatus()
                .isFound();
    }

    @Test
    void 로그인_성공() {
        postRequest("/login")
                .body(WebTestHelper.loginForm("test@gmail.com",
                        "Password!1"))
                .exchange()
                .expectHeader()
                .valueMatches("location", ".*/;jsessionid=([\\d\\w]+)")
                .expectStatus()
                .is3xxRedirection();
    }
}