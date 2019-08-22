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
        assertThat(getRequest("/api/articles/11").getStatus().is4xxClientError()).isTrue();
    }

    @Test
    void update_correctArticleDto_isOk() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(1));
        params.put("contents", "moomin is not moomin anymore");
        assertThat(putJsonRequest("/api/articles", params).getStatus().is2xxSuccessful()).isTrue();

        Map<String, String> params2 = new HashMap<>();
        params2.put("id", String.valueOf(1));
        params2.put("contents", "moomin is moomin");
        assertThat(putJsonRequest("/api/articles", params2).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void update_incorrectArticleDto_exception() {
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(11));
        params.put("contents", "moomin is not moomin anymore");

        assertThat(putJsonRequest("/api/articles", params).getStatus().is4xxClientError()).isTrue();
    }
}