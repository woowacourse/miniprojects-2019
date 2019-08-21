package techcourse.w3.woostagram.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserContentsDto;
import techcourse.w3.woostagram.user.dto.UserDto;
import techcourse.w3.woostagram.user.dto.UserUpdateDto;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends AbstractControllerTests {
    private static final String TEST_EMAIL = "a@naver.com";
    private static final String TEST_EMAIL2 = "test2@test.com";
    private static final String TEST_PASSWORD = "Aa1234!!";
    private static final String BAD_PASSWORD = "abc";

    @Override
    @BeforeEach
    protected void setUp() {
        loginRequest(TEST_EMAIL, TEST_PASSWORD);
    }

    @Test
    void loginForm_empty_isOk() {
        clearCookie();
        assertThat(getRequest("/users/login/form")
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }

    @Test
    void login_correct_isRedirect() {
        Map<String, String> params = new HashMap<>();
        params.put("email", TEST_EMAIL);
        params.put("password", TEST_PASSWORD);

        clearCookie();
        assertThat(postFormRequest("/users/login", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void login_mismatch_isFail() {
        Map<String, String> params = new HashMap<>();
        params.put("email", TEST_EMAIL2);
        params.put("password", TEST_PASSWORD);

        clearCookie();
        assertThat(postFormRequest("/users/login", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void createForm_empty_isOk() {
        clearCookie();
        assertThat(getRequest("/users/signup/form")
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }

    @Test
    void create_correct_isOk() {
        Map<String, String> params = new HashMap<>();
        params.put("email", TEST_EMAIL2);
        params.put("password", TEST_PASSWORD);

        clearCookie();
        assertThat(postFormRequest("/users/signup", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void create_mismatch_isFail() {
        Map<String, String> params = new HashMap<>();
        params.put("email", TEST_EMAIL2);
        params.put("password", BAD_PASSWORD);

        clearCookie();
        assertThat(postFormRequest("/users/signup", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void show_correct_isOk() {
        assertThat(getRequest("/users/mypage")
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }

    @Test
    void updateForm_correct_isOk() {
        assertThat(getRequest("/users/mypage-edit/form")
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }

    @Test
    void update_correct_isOk() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "a");
        params.put("userName", "b");
        params.put("contents", "c");
        params.put("originalImageFile", "d");
        params.put("imageFile", "e");

        assertThat(putFormRequest("/users", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void update_mismatch_isFail() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "");
        params.put("userName", "");
        params.put("contents", "");
        params.put("originalImageFile", "");
        params.put("imageFile", "");

        assertThat(putFormRequest("/users", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void delete_correct_isOk() {
        Map<String, String> params = new HashMap<>();
        params.put("email", TEST_EMAIL2);
        params.put("password", TEST_PASSWORD);
        postFormRequest("/users/signup", params);
        loginRequest(TEST_EMAIL2, TEST_PASSWORD);

        assertThat(deleteRequest("/users")
                .getStatus()
                .is3xxRedirection()).isTrue();
    }
}