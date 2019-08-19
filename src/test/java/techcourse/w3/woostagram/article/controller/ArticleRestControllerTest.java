package techcourse.w3.woostagram.article.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.article.dto.ArticleDto;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleRestControllerTest extends AbstractControllerTests {
    @Test
    void read_correctArticleId_isOk() {
        ArticleDto response = getRequest("/api/articles/1", ArticleDto.class);
        assertThat(response.getContents()).isEqualTo("moomin is moomin");
    }

    @Test
    void read_incorrectArticleId_exception() {
        assertThat(getRequest("/api/articles/2").getStatus().is4xxClientError()).isTrue();
    }

    @Test
    void update_correctArticleDto_isOk() {
        assertThat(putJsonRequest("/api/articles", ArticleDto.class,
                null, String.valueOf(1), "moomin is not moomin anymoer", null, null).getStatus().is2xxSuccessful()).isTrue();
        putJsonRequest("/api/articles", ArticleDto.class,
                null, String.valueOf(1), "moomin is moomin", null, null);
    }

    @Test
    void update_incorrectArticleDto_exception() {
        assertThat(putJsonRequest("/api/articles", ArticleDto.class,
                null, String.valueOf(2), "moomin is not moomin anymoer", null, null).getStatus().is4xxClientError()).isTrue();
    }
}