package techcourse.w3.woostagram.comment.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.comment.dto.CommentDto;

import static org.assertj.core.api.Assertions.assertThat;

class RestCommentControllerTest extends AbstractControllerTests {
    @Test
    void create_correct_isOk() {
        assertThat(postJsonRequest("/api/comments/1", CommentDto.class,
                null, "contents", null, null).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void read_correct_isOk() throws NoSuchFieldException {
        CommentDto[] response = getRequest("/api/comments/1", CommentDto[].class);
        assertThat(response[0].getContents()).isEqualTo("내용");
    }
}