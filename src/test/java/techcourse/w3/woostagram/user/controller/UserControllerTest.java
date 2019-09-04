package techcourse.w3.woostagram.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import techcourse.w3.woostagram.AbstractControllerTests;
import techcourse.w3.woostagram.common.support.TestDataInitializer;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends AbstractControllerTests {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String USER_NAME = "userName";
    private static final String CONTENTS = "contents";

    @Override
    @BeforeEach
    protected void setUp() {
        loginRequest(TestDataInitializer.authorUser.getEmail(), TestDataInitializer.authorUser.getPassword());
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
        params.put(EMAIL, TestDataInitializer.authorUser.getEmail());
        params.put(PASSWORD, TestDataInitializer.authorUser.getPassword());

        clearCookie();
        assertThat(postFormRequest("/users/login", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void login_mismatch_isFail() {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL, TestDataInitializer.unAuthorUser.getEmail());
        params.put(PASSWORD, TestDataInitializer.unAuthorUser.getPassword());

        clearCookie();
        assertThat(postFormRequest("/users/login", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void logout_empty_isRedirect() {
        assertThat(getRequest("/users/logout")
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
        params.put(EMAIL, TestDataInitializer.authorUser.getEmail());
        params.put(PASSWORD, TestDataInitializer.authorUser.getPassword());

        clearCookie();
        assertThat(postFormRequest("/users/signup", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void create_mismatch_isFail() {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL, TestDataInitializer.authorUser.getEmail());
        params.put(PASSWORD, TestDataInitializer.unAuthorUser.getPassword());

        clearCookie();
        assertThat(postFormRequest("/users/signup", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void updateForm_correct_isOk() {
        assertThat(getRequest("/users/mypage-edit/form")
                .getStatus()
                .is2xxSuccessful()).isTrue();
    }

    @Test
    void update_correct_isOk() {
        loginRequest(TestDataInitializer.updateUser.getEmail(), TestDataInitializer.updateUser.getPassword());
        Map<String, String> params = new HashMap<>();
        params.put(NAME, "a");
        params.put(USER_NAME, "b");
        params.put(CONTENTS, "c");

        assertThat(putFormRequest("/users", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void update_mismatch_isFail() {
        Map<String, String> params = new HashMap<>();
        params.put(NAME, "");
        params.put(USER_NAME, "");
        params.put(CONTENTS, "");

        assertThat(putFormRequest("/users", params)
                .getStatus()
                .is3xxRedirection()).isTrue();
    }

    @Test
    void delete_correct_isOk() {
        Map<String, String> params = new HashMap<>();
        params.put(EMAIL, TestDataInitializer.deleteUser.getEmail());
        params.put(PASSWORD, TestDataInitializer.deleteUser.getPassword());
        postFormRequest("/users/signup", params);
        clearCookie();
        loginRequest(TestDataInitializer.deleteUser.getEmail(), TestDataInitializer.deleteUser.getPassword());

        assertThat(deleteRequest("/users")
                .getStatus()
                .is3xxRedirection()).isTrue();
    }
}