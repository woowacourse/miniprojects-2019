package techcourse.w3.woostagram.follow.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class FollowRestControllerTest extends AbstractControllerTests {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        postJsonRequest("/api/follows/2", new HashMap<>());
    }

    @Test
    void readFollower_correctId_isTrue() {
        UserInfoDto[] userInfoDtos = getRequest("/api/follows/2/followers", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getId()).isEqualTo(1);
    }

    @Test
    void readFollowing_correctId_isTrue() {
        UserInfoDto[] userInfoDtos = getRequest("/api/follows/1/followings", UserInfoDto[].class);
        assertThat(userInfoDtos[0].getId()).isEqualTo(2);
    }

    @Test
    void create_correctTarget_isTrue() {
        assertThat(postJsonRequest("/api/follows/2", new HashMap<>()).getStatus().is2xxSuccessful()).isTrue();

    }

    @Test
    void delete_correctTarget_isTrue() {
        assertThat(deleteRequest("/api/follows/2").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void readNumberOfFollowers_correctId_isTrue() {
        int numberOfFollowers = getRequest("/api/follows/num/2/followers", Integer.class);
        assertThat(numberOfFollowers).isEqualTo(1);
    }

    @Test
    void readNumberOfFollowing_correctId_isTrue() {
        int numberOfFollowing = getRequest("/api/follows/num/1/followings", Integer.class);
        assertThat(numberOfFollowing).isEqualTo(1);
    }

    @Override
    @AfterEach
    protected void tearDown() {
        super.tearDown();
        deleteRequest("/api/follows/2");
    }
}