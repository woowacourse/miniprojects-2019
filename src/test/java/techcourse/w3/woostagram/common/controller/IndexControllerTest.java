package techcourse.w3.woostagram.common.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;

import static org.assertj.core.api.Assertions.assertThat;

class IndexControllerTest extends AbstractControllerTests {
    @Test
    void index_correct_isOk() {
        assertThat(getRequest("/").getStatus().is2xxSuccessful()).isTrue();
    }
}