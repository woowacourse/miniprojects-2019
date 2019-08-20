package techcourse.w3.woostagram.follow.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class FollowRestControllerTest extends AbstractControllerTests {
    @Test
    void readFollower_correctId_isTrue() {
        postJsonRequest("/api/follow/2", UserInfoDto.class, String.valueOf(2), "moomin@naver.com", "");
        UserInfoDto[] userInfoDtos = getRequest("/api/follow/from/2", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getId()).isEqualTo(1);
        deleteRequest("/api/follow/2");
    }

    @Test
    void readFollowing_correctId_isTrue(){
        postJsonRequest("/api/follow/2", UserInfoDto.class, String.valueOf(2), "moomin@naver.com", "");
        UserInfoDto[] userInfoDtos = getRequest("/api/follow/to/1", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getId()).isEqualTo(2);
        deleteRequest("/api/follow/2");
    }

    @Test
    void create_correctTarget_isTrue(){
        assertThat(postJsonRequest("/api/follow/2", UserInfoDto.class, String.valueOf(2), "moomin@naver.com", "").getStatus().is2xxSuccessful()).isTrue();
        deleteRequest("/api/follow/2");
    }

    @Test
    void delete_correctTarget_isTrue(){
        postJsonRequest("/api/follow/2", UserInfoDto.class, String.valueOf(2), "moomin@naver.com", "");
        assertThat(deleteRequest("/api/follow/2").getStatus().is2xxSuccessful()).isTrue();
    }
}