package techcourse.w3.woostagram.comment.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.comment.dto.CommentDto;

import static org.assertj.core.api.Assertions.assertThat;

class RestCommentControllerTest extends AbstractControllerTests {
    @Test
    void create_correct_isOk() {
        assertThat(postJsonRequest("/api/comments", CommentDto.class,
                null, "contents", null, null, String.valueOf(1)).getStatus().is2xxSuccessful()).isTrue();
    }
}