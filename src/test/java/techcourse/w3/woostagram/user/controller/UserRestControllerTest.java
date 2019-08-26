package techcourse.w3.woostagram.user.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRestControllerTest extends AbstractControllerTests {
    @Test
    void delete_correct_isOk() {
        assertThat(deleteRequest("api/users").getStatus().is2xxSuccessful()).isTrue();
    }
}
