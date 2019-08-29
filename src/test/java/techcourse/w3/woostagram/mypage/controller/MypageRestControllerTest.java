package techcourse.w3.woostagram.mypage.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import static org.assertj.core.api.Assertions.assertThat;

class MypageRestControllerTest extends AbstractControllerTests {

    @Test
    void read_userNameAndPage_isOk() {
        assertThat(getRequest("/api/mypage/users/" + TestDataInitializer.authorUser.getUserContents().getUserName() + "?page=0&size=1")
                .getStatus()
                .is2xxSuccessful()).isTrue();

    }
}