package techcourse.w3.woostagram.search.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import static org.assertj.core.api.Assertions.assertThat;

class SearchRestControllerTest extends AbstractControllerTests {
    @Test
    void read_correctQueryUser_isOk() {
        assertThat(getRequest("/api/search/" + TestDataInitializer.authorUser.getUserContents().getUserName())
                .getStatus().is2xxSuccessful()).isTrue();
    }
}