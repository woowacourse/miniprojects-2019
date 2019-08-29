package techcourse.w3.woostagram.mypage.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import static org.assertj.core.api.Assertions.assertThat;

class MypageControllerTest extends AbstractControllerTests {
    @Test
    void showUserPage_userNameAndEmail_isOk() {
        assertThat(getRequest("/" + TestDataInitializer.authorUser.getUserContents().getUserName())
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }
}