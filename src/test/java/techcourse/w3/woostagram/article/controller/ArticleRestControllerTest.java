package techcourse.w3.woostagram.article.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.article.dto.ArticleDto;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleRestControllerTest extends AbstractControllerTests {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void read_correctArticleId_isOk() throws IOException {
        String json = new String(Objects.requireNonNull(getRequest("/api/articles/1").getResponseBody()));
        ArticleDto response = objectMapper.readValue(json, ArticleDto.class);
        assertThat(response.getContents()).isEqualTo("moomin is moomin");
    }

    @Test
    void read_incorrectArticleId_exception() {
        assertThat(getRequest("/api/articles/2").getStatus().is4xxClientError()).isTrue();
    }

    @Test
    void update_correctArticleDto_isOk() {
        assertThat(putJsonRequest("/api/articles", ArticleDto.class,
                String.valueOf(1), "moomin is not moomin anymoer", null, null, null).getStatus().is2xxSuccessful()).isTrue();
        putJsonRequest("/api/articles", ArticleDto.class,
                String.valueOf(1), "moomin is moomin", null, null, null);
    }

    @Test
    void update_incorrectArticleDto_exception() {
        assertThat(putJsonRequest("/api/articles", ArticleDto.class,
                String.valueOf(2), "moomin is not moomin anymoer", null, null, null).getStatus().is4xxClientError()).isTrue();
    }
}