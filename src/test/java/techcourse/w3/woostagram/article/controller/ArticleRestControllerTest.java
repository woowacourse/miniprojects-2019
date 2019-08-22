package techcourse.w3.woostagram.article.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleRestControllerTest extends AbstractControllerTests {
    @Test
    void read_correctArticleId_isOk() {
        ArticleDto response = getRequest("/api/articles/" + TestDataInitializer.basicArticle.getId(), ArticleDto.class);
        assertThat(response.getContents()).isEqualTo(TestDataInitializer.basicArticle.getContents());
    }

    @Test
    void read_incorrectArticleId_exception() {
        assertThat(getRequest("/api/articles/11231").getStatus().is4xxClientError()).isTrue();
    }

    @Test
    void update_correctArticleDto_isOk() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(TestDataInitializer.updateArticle.getId()));
        params.put("contents", "moomin is not moomin anymore");
        assertThat(putJsonRequest("/api/articles", params).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void update_incorrectArticleDto_exception() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(11231));
        params.put("contents", "moomin is not moomin anymore");
        assertThat(putJsonRequest("/api/articles", params).getStatus().is4xxClientError()).isTrue();
    }
}