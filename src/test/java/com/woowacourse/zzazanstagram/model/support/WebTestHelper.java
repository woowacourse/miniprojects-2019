package com.woowacourse.zzazanstagram.model.support;

import org.springframework.web.reactive.function.BodyInserters;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

public class WebTestHelper {
    public static BodyInserters.FormInserter<String> userSignUpForm(String email, String name, String profile, String nickName, String password) {
        return fromFormData("email", email)
                .with("name", name)
                .with("profile", profile)
                .with("nickName", nickName)
                .with("password", password);
    }
}
