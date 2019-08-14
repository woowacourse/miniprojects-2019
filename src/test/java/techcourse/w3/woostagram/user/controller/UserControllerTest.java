package techcourse.w3.woostagram.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.user.dto.UserContentsDto;
import techcourse.w3.woostagram.user.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends AbstractControllerTests {
    private static final String TEST_EMAIL = "a@naver.com";
    private static final String TEST_EMAIL2 = "test2@test.com";
    private static final String TEST_PASSWORD = "Aa1234!!";
    private static final String BAD_PASSWROD = "abc";

    @BeforeEach
    void setUp() {
        loginRequest(TEST_EMAIL, TEST_PASSWORD);
    }

    @Test
    void loginForm_empty_isOk() {
        assertThat(getRequest("/users/login/form")
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }

    @Test
    void login_correct_isOk() {
        assertThat(postFormRequest("/users/login", UserDto.class, TEST_EMAIL, TEST_PASSWORD)
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }

    @Test
    void login_mismatch_isFail() {
        assertThat(postFormRequest("/users/login", UserDto.class, TEST_EMAIL2, TEST_PASSWORD)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void createForm_empty_isOk() {
        assertThat(getRequest("/users/signup/form")
                .getStatus()
                .is2xxSuccessful())
                .isTrue();
    }

    @Test
    void create_correct_isOk() {
        assertThat(postFormRequest("/users/signup", UserDto.class, TEST_EMAIL2, TEST_PASSWORD)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void create_mismatch_isFail() {
        assertThat(postFormRequest("/users/signup", UserDto.class, TEST_EMAIL2, BAD_PASSWROD)
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
        assertThat(putFormRequest("/users", UserContentsDto.class, "a", "b", "c", "d")
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void update_mismatch_isFail() {
        assertThat(putFormRequest("/users", UserContentsDto.class, "", "", "", "")
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void delete_correct_isOk() {
        postFormRequest("/users/signup", UserDto.class, TEST_EMAIL2, TEST_PASSWORD);
        loginRequest(TEST_EMAIL2, TEST_PASSWORD);

        assertThat(deleteRequest("/users")
                .getStatus()
                .is3xxRedirection()).isTrue();
    }
}