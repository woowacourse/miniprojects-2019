package techcourse.w3.woostagram.comment.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.comment.dto.CommentDto;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CommentRestControllerTest extends AbstractControllerTests {
    @Test
    void create_correct_isOk() {
        Map<String, String> params = new HashMap<>();
        params.put("contents", "contents");
        assertThat(postJsonRequest(String.format("/api/articles/%d/comments",
                TestDataInitializer.basicArticle.getId()), params).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void read_correct_isOk() {
        CommentDto[] response = getRequest(String.format("/api/articles/%d/comments",
                TestDataInitializer.basicArticle.getId()), CommentDto[].class);
        assertThat(response[0].getContents()).isEqualTo(TestDataInitializer.basicComment.getContents());
    }

    @Test
    void delete_correct_isOk() {
        assertThat(deleteRequest(String.format("/api/articles/%d/comments/%d",
                TestDataInitializer.basicArticle.getId(),
                TestDataInitializer.deleteComment.getId()))
                .getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void delete_notCorrect_isFail() {
        clearCookie();
        loginRequest(TestDataInitializer.unAuthorUser.getEmail(), TestDataInitializer.unAuthorUser.getPassword());

        assertThat(deleteRequest(String.format("/api/articles/%d/comments/%d",
                TestDataInitializer.basicArticle.getId(),
                TestDataInitializer.basicComment.getId()))
                .getStatus().is4xxClientError()).isTrue();
    }
}