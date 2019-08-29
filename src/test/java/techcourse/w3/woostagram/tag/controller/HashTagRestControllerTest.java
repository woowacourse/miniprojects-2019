package techcourse.w3.woostagram.tag.controller;

import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import static org.assertj.core.api.Assertions.assertThat;

class HashTagRestControllerTest extends AbstractControllerTests {
    @Test
    void read_correctHashTagName_isOk() {
        assertThat(getRequest("/api/tags/hash/" + TestDataInitializer.basicTag.getName().substring(1) + "?page=0&size=1")
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }
}