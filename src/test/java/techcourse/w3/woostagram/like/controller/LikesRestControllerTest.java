package techcourse.w3.woostagram.like.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import static org.assertj.core.api.Assertions.assertThat;

class LikesRestControllerTest extends AbstractControllerTests {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void create_correct_isOk() {
        assertThat(postJsonRequest("/api/like/1", UserInfoDto.class, String.valueOf(1), "a@naver.com", "")
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }
}