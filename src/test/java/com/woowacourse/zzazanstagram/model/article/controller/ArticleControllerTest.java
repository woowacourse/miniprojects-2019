package com.woowacourse.zzazanstagram.model.article.controller;

import com.woowacourse.zzazanstagram.model.RequestTemplate;
import com.woowacourse.zzazanstagram.model.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;

import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.*;

class ArticleControllerTest extends RequestTemplate {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 게시글_등록_페이지_이동_테스트() {
        getHeaderWithLogin("/articles/new")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void 게시글_등록이_되는지_테스트() {
        postHeaderWithLogin("/articles")
                .body(BodyInserters.fromFormData("imageUrl", IMAGE_URL)
                        .with("contents", CONTENTS)
                        .with("hashTag", HASHTAG))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", "http://localhost:[0-9]+/");
    }
}