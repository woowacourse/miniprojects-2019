package techcourse.w3.woostagram.like.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static techcourse.w3.woostagram.common.support.TestDataInitializer.authorUser;

class LikesRestControllerTest extends AbstractControllerTests {

    @Test
    void create_correct_isOk() {
        assertThat(postJsonRequest("/api/articles/2/likes", new HashMap<>())
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }

    @Test
    void readLikedUser_correct_isOk() {
        UserInfoDto[] userInfoDtos = getRequest("/api/articles/1/likes", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getEmail()).isEqualTo(authorUser.getEmail());
    }

    @Test
    void delete_correct_isOk() {
        assertThat(deleteRequest("/api/articles/2/likes").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void readNumberOfLiked_correct_isOk() {
        int numberOfLiked = getRequest("/api/articles/1/likes/num", Integer.class);
        assertThat(numberOfLiked).isEqualTo(1);
    }
}