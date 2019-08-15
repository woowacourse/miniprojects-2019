package com.woowacourse.zzazanstagram.model.article.domain;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Image;
import com.woowacourse.zzazanstagram.model.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.CONTENTS;
import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArticleTest {

    private Image image;
    private Contents contents;
    private Article article;
    private Member author;

    @BeforeEach
    void setUp() {
        image = Image.of(IMAGE_URL);
        contents = Contents.of(CONTENTS);
        author = Member.MemberBuilder.aMember()
                .nickName("nickName")
                .name("name")
                .email("test@test.com")
                .password("password1!")
                .profile("https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-600w-1029171697.jpg")
                .build();
        article = Article.from(image, contents, author);
    }

    @Test
    void create() {
        assertThat(article).isEqualTo(Article.from(image, contents, author));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sdfsdfsdf", "notUrl"})
    void 이미지_url_비정상_체크(String imageUrl) {
        assertThrows(IllegalArgumentException.class, () ->
                Article.from(Image.of(imageUrl), contents, author));
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://image.shutterstock.com/image-photo/white-transparent-leaf-on-mirror-600w-1029171697.jpg", "https://image.shutterstock.com/image-photo/bright-spring-view-cameo-island-600w-1048185397.jpg"})
    void 이미지_url_정상_체크(String imageUrl) {
        assertThatCode(() ->
                Article.from(Image.of(imageUrl), contents, author))
                .doesNotThrowAnyException();
    }
}