package com.woowacourse.zzazanstagram.model.article;

import com.woowacourse.zzazanstagram.model.article.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.vo.ImageUrl;
import com.woowacourse.zzazanstagram.model.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleTest {
    private static final String IMAGE_URL = "https://avatars2.githubusercontent.com/u/44018338?s=400&v=4";
    private static final String CONTENTS = "글의 내용이란다";

    private ImageUrl imageUrl;
    private Contents contents;
    private Member member;
    private Article article;

    @BeforeEach
    void setUp() {
        imageUrl = new ImageUrl(IMAGE_URL);
        contents = new Contents(CONTENTS);
        member = new Member();
        article = new Article(imageUrl, contents, member);
    }

    @Test
    void create() {
        assertThat(article).isEqualTo(new Article(imageUrl, contents, member));
    }
}