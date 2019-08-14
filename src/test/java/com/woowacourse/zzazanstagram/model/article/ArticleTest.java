package com.woowacourse.zzazanstagram.model.article;

import com.woowacourse.zzazanstagram.model.article.domain.Article;
import com.woowacourse.zzazanstagram.model.article.domain.vo.Contents;
import com.woowacourse.zzazanstagram.model.article.domain.vo.ImageUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.CONTENTS;
import static com.woowacourse.zzazanstagram.model.article.ArticleConstant.IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;

class ArticleTest {

    private ImageUrl imageUrl;
    private Contents contents;
    private Article article;

    @BeforeEach
    void setUp() {
        imageUrl = ImageUrl.of(IMAGE_URL);
        contents = Contents.of(CONTENTS);
        article = Article.from(imageUrl, contents);
    }

    @Test
    void create() {
        assertThat(article).isEqualTo(Article.from(imageUrl, contents));
    }
}