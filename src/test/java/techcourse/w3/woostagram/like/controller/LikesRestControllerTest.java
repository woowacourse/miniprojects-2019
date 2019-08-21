package techcourse.w3.woostagram.like.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class LikesRestControllerTest extends AbstractControllerTests {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        postJsonRequest("/api/likes/1", new HashMap<>());
    }

    @Test
    void create_correct_isOk() {
        assertThat(postJsonRequest("/api/likes/1", new HashMap<>())
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }

    @Test
    void readLikedUser_correct_isOk() {
        UserInfoDto[] userInfoDtos = getRequest("/api/likes/1", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getEmail()).isEqualTo("a@naver.com");
    }

    @Test
    void delete_correct_isOk() {
        assertThat(deleteRequest("/api/likes/1/1").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void readNumberOfLiked_correct_isOk() {
        int numberOfLiked = getRequest("/api/likes/num/1", Integer.class);
        assertThat(numberOfLiked).isEqualTo(1);
    }
}