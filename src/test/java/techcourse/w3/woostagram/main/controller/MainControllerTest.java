package techcourse.w3.woostagram.main.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class MainControllerTest extends AbstractControllerTests {
    @Test
    public void read_correctPageable_isOk() {
        String json = new String(Objects.requireNonNull(getRequest("/api/main").getResponseBody()));
        List<String> articles = JsonPath.read(json, "$.content.*");
        assertThat(articles.size()).isEqualTo(3);
    }
}
