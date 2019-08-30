package techcourse.w3.woostagram.expolr.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ArticleSearchControllerTest extends AbstractControllerTests {

    @Test
    void readIndex_correctPageable_isOk() {
        String json = new String(Objects.requireNonNull(getRequest("/api/main?page=0&size=2").getResponseBody()));
        List<String> articles = JsonPath.read(json, "$.content.*");
        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    void readUser_userNameAndPage_isOk() {
        assertThat(getRequest("/api/mypage/users/" + TestDataInitializer.authorUser.getUserContents().getUserName() + "?page=0&size=1")
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }

    @Test
    void readTagging_correctHashTagName_isOk() {
        assertThat(getRequest("/api/tags/hash/" + TestDataInitializer.basicTag.getName().substring(1) + "?page=0&size=1")
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }

    @Test
    void readLikesArticle() {
        assertThat(getRequest("/api/likes?page=0&size=2")
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }
}