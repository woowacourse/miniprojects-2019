package techcourse.w3.woostagram.follow.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static techcourse.w3.woostagram.common.support.TestDataInitializer.*;

class FollowRestControllerTest extends AbstractControllerTests {
    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void readFollower_correctId_isTrue() {
        UserInfoDto[] followers =
                getRequest("/api/users/" + basicFollow.getFrom().getId() + "/follows/followers", UserInfoDto[].class);
        assertThat(followers[0].getId()).isEqualTo(unAuthorUser.getId());
    }

    @Test
    void readFollowing_correctId_isTrue() {
        UserInfoDto[] followings =
                getRequest("/api/users/" + basicFollow.getTo().getId() + "/follows/followings", UserInfoDto[].class);
        assertThat(followings[0].getId()).isEqualTo(authorUser.getId());
    }

    @Test
    void create_correctTarget_isTrue() {
        assertThat(postJsonRequest("/api/users/" + unAuthorUser.getId() + "/follows"
                , new HashMap<>()).getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void delete_correctTarget_isTrue() {
        assertThat(deleteRequest("/api/users/" + deleteFollow.getFrom().getId() + "/follows").getStatus().is2xxSuccessful()).isTrue();
    }

    @Test
    void readNumberOfFollowers_correctId_isTrue() {
        int numberOfFollowers = getRequest("/api/users/" + basicFollow.getFrom().getId() + "/follows/followers/num", Integer.class);
        assertThat(numberOfFollowers).isEqualTo(1);
    }

    @Test
    void readNumberOfFollowing_correctId_isTrue() {
        int numberOfFollowing = getRequest("/api/users/" + basicFollow.getTo().getId() + "/follows/followings/num", Integer.class);
        assertThat(numberOfFollowing).isEqualTo(1);
    }
}