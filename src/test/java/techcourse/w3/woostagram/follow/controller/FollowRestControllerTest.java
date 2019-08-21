package techcourse.w3.woostagram.follow.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import static org.assertj.core.api.Assertions.assertThat;

class FollowRestControllerTest extends AbstractControllerTests {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        postJsonRequest("/api/follow/2", UserInfoDto.class, String.valueOf(2), "moomin@naver.com", "");
    }

    @Test
    void readFollower_correctId_isTrue() {
        UserInfoDto[] userInfoDtos = getRequest("/api/follow/from/2", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getId()).isEqualTo(1);
    }

    @Test
    void readFollowing_correctId_isTrue() {
        UserInfoDto[] userInfoDtos = getRequest("/api/follow/to/1", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getId()).isEqualTo(2);
    }

    @Test
    void create_correctTarget_isTrue() {
        assertThat(postJsonRequest("/api/follow/2", UserInfoDto.class, String.valueOf(2), "moomin@naver.com", "").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void delete_correctTarget_isTrue() {
        assertThat(deleteRequest("/api/follow/2").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void readNumberOfFollowers_correctId_isTrue() {
        int numberOfFollowers = getRequest("/api/follow/num/from/2", Integer.class);
        assertThat(numberOfFollowers).isEqualTo(1);
    }

    @Test
    void readNumberOfFollowing_correctId_isTrue() {
        int numberOfFollowing = getRequest("/api/follow/num/to/1", Integer.class);
        assertThat(numberOfFollowing).isEqualTo(1);
    }

    @Override
    @AfterEach
    protected void tearDown() {
        super.tearDown();
        deleteRequest("/api/follow/2");
    }
}