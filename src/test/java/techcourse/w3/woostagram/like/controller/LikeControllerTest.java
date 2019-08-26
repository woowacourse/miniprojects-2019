package techcourse.w3.woostagram.like.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;

import static org.assertj.core.api.Assertions.assertThat;

public class LikeControllerTest extends AbstractControllerTests {
    @Test
    void index_empty_ok() {
        assertThat(getRequest("/likes").getStatus().is2xxSuccessful()).isTrue();
    }
}
